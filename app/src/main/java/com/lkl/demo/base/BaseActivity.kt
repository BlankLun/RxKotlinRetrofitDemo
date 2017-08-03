package com.lkl.demo.base

import android.content.Context
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created    likunlun
 * Time       2017/8/3 11:58
 * Desc	      Activity基类，继承自RxAppCompatActivity
 */
abstract class BaseActivity : RxAppCompatActivity() {
    protected var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        // 接收传参
        preInitView()
        // 设置布局
        if (0 != getLayoutResId()) {
            setContentView(getLayoutResId())
        }
        // 初始化子View
        initView()
        // 初始化View业务
        initEvent(savedInstanceState)
        // 初始化事件监听
        initViewListener()
    }

    abstract fun preInitView()
    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initEvent(savedInstanceState: Bundle?)
    abstract fun initViewListener()
}
