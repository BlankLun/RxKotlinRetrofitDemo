package com.lkl.networklibrary.https

import android.util.Log
import com.lkl.networklibrary.https.consts.UriConsts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      设置ApiService
 */
object Api {
    /**
     * 请求超时时间
     */
    private val DEFAULT_TIMEOUT = 5L

    private var SERVICE: ApiService? = null

    @JvmStatic
    val default: ApiService
        get() {
            if (SERVICE == null) {
                val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    //打印retrofit日志
                    Log.d("RetrofitLog", "retrofitBack = " + message)
                })
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                //手动创建一个OkHttpClient并设置超时时间
                val client = OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(RequestInterceptor())
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .build()

                SERVICE = Retrofit.Builder()
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(UriConsts.BASE_URL)
                        .build()
                        .create(ApiService::class.java)
            }
            return SERVICE!!
        }
}
