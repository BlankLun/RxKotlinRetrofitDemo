package com.lkl.networklibrary.https

import com.lkl.networklibrary.bean.UserInfoVo
import com.lkl.networklibrary.https.util.MD5Util
import com.lkl.networklibrary.https.util.compose1
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      desc
 */
class HttpUtil private constructor() {
    companion object {
        @JvmStatic
        val instance: HttpUtil by lazy { HttpUtil() }
    }

    private val mApiService: ApiService = Api.default

    fun <E> userLogin(userName: String, password: String,
                      lifecycleProvider: LifecycleProvider<E>,
                      httpSimpleSubscriber: HttpSimpleSubscriber<UserInfoVo>? = null): Observable<UserInfoVo> {
        var requestParam = HashMap<String, String>()
        requestParam.put("username", userName)
        requestParam.put("password", MD5Util.MD5(password))
        return mApiService.userLogin(requestParam)
                .compose1(lifecycleProvider, httpSimpleSubscriber = httpSimpleSubscriber)
    }
}