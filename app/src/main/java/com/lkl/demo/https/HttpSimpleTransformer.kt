package com.lkl.demo.https

import android.util.Log
import com.lkl.demo.bean.ResultData
import com.lkl.demo.https.consts.HttpStatus
import com.lkl.demo.https.exception.ApiException
import com.lkl.demo.https.exception.NullDataException
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      统一变换类，Observable<ResultData<T>> --> Observable<T>，加入了生命周期监控
 */
class HttpSimpleTransformer<T, E>(private var lifecycleProvider: LifecycleProvider<E>? = null,
                                  private var needCertification: Boolean = false,
                                  private var httpSimpleSubscriber: HttpSimpleSubscriber<T>? = null)
    : ObservableTransformer<ResultData<T>, T> {

    companion object {
        private val TAG = "HttpSimpleTransformer"
    }

    override fun apply(upstream: Observable<ResultData<T>>): ObservableSource<T> {
        if (lifecycleProvider is RxAppCompatActivity) {
            val rxAppCompatActivity = lifecycleProvider as RxAppCompatActivity
            upstream.compose(rxAppCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
        } else if (lifecycleProvider is RxFragment) {
            val rxFragment = lifecycleProvider as RxFragment
            upstream.compose(rxFragment.bindUntilEvent(FragmentEvent.DESTROY))
        }
        val observable = upstream
                .flatMap { tResultData -> flatResponse(tResultData) }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    //不能破坏链式结构，否则不起作用
                    if (httpSimpleSubscriber is ProgressSubscriber) {
                        Log.d(TAG, "doOnSubscribe")
                        val progressSubscriber = httpSimpleSubscriber as ProgressSubscriber
                        progressSubscriber.showProgressDialog()
                    }
                }
        if (null != httpSimpleSubscriber) {
            observable.subscribe(httpSimpleSubscriber!!)
        }
        return observable
    }

    private fun flatResponse(response: ResultData<T>): Observable<T> {
        return Observable.create(ObservableOnSubscribe<T> { subscriber ->
            try {
                if (response.code == HttpStatus.SUCCESS) {//请求成功
                    if (null != response.data) {
                        subscriber.onNext(response.data!!)
                    } else {
                        subscriber.onError(NullDataException("返回数据为空"))
                    }
                } else {//请求失败
                    if (needCertification) {
                        //需要认证，需要认证的接口根据需要选择是否要做统一的额外逻辑处理
                        subscriber.onError(ApiException(response.code))
                    } else {
                        //这里抛出自定义的一个异常.可以处理服务器返回的错误.
                        subscriber.onError(ApiException(response.code))
                    }
                    return@ObservableOnSubscribe
                }
                //请求完成
                subscriber.onComplete()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                subscriber.onError(e)
            }
        })
    }
}