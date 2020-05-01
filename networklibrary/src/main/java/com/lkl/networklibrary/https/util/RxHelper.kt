package com.lkl.networklibrary.https.util

import com.lkl.networklibrary.bean.ResultData
import com.lkl.networklibrary.https.HttpSimpleSubscriber
import com.lkl.networklibrary.https.HttpSimpleTransformer
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      Observable扩展方法
 */
fun <T : Any, E> Observable<ResultData<T>>.compose1(lifecycleProvider: LifecycleProvider<E>? = null,
                                                    needCertification: Boolean = false,
                                                    httpSimpleSubscriber: HttpSimpleSubscriber<T>? = null): Observable<T> =
        compose(HttpSimpleTransformer(lifecycleProvider, needCertification, httpSimpleSubscriber))