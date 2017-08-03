package com.lkl.demo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates


/**
 * 创建者     谌坤
 * 创建时间   2017/5/25 18:07
 * 描述	      ${TODO}
 */
class MyApplication : Application() {

    companion object {

        private val TAG = "MyApplication"

        @JvmStatic
        var context: Context by Delegates.notNull()
            private set

        @JvmStatic
        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as MyApplication
            return application.refWatcher
        }
    }

    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        //初始化LeakCanary
        refWatcher = installLeakCanary()
    }

    protected fun installLeakCanary(): RefWatcher {
        if (BuildConfig.DEBUG) {
            return LeakCanary.install(this)
        } else {
            //release版本
            return RefWatcher.DISABLED
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }
}
