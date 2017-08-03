package com.lkl.demo.https

import android.util.Log
import com.lkl.demo.https.consts.HttpStatus
import com.lkl.demo.https.exception.ApiException
import com.lkl.demo.https.exception.NullDataException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      统一订阅者类
 */
abstract class HttpSimpleSubscriber<T> : Observer<T> {
    companion object {
        private val TAG = "HttpSimpleSubscriber"
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        _onNext(t)
    }

    override fun onError(t: Throwable) {
        var errorCode = HttpStatus.ERROR
        var msg = "请求失败，请稍后再试..."
        if (t is NullDataException) {
            _onNext(null)
            return
        } else if (t is ApiException) {
            errorCode = t.errorCode
            msg = t.message ?: "请求失败，请稍后再试..."
        } else if (t is UnknownHostException) {
            errorCode = HttpStatus.SOCKET_TIMEOUT
            msg = "请检查网络"
        } else if (t is SocketTimeoutException) {
            errorCode = HttpStatus.SOCKET_TIMEOUT
            msg = "网络连接超时，请稍后再试..."
        } else if (t is ConnectException) {
            errorCode = HttpStatus.SOCKET_TIMEOUT
            msg = "网络连接失败，请稍后再试..."
        }
        Log.e(TAG, "errorCode:${errorCode} " + t)
        _onError(errorCode, msg)
    }

    override fun onComplete() {
        _onComplete()
    }

    abstract fun _onNext(retData: T?)

    open fun _onComplete() {

    }

    abstract fun _onError(errorCode: Int, msg: String)
}