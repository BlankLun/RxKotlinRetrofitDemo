package com.lkl.networklibrary.https


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      请求拦截器，设置统一的请求参数
 */
class RequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
                .header("Cache-Control", "no-cache")
                .method(original.method(), original.body())
                .build()
        return chain.proceed(request)
    }
}
