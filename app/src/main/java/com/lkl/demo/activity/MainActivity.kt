package com.lkl.demo.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.lkl.demo.R
import com.lkl.demo.base.BaseActivity
import com.lkl.demo.bean.UserInfoVo
import com.lkl.demo.https.HttpUtil
import com.lkl.demo.https.ProgressSubscriber

class MainActivity : BaseActivity() {

    private var btn: Button? = null

    override fun preInitView() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main_layout
    }

    override fun initView() {
        btn = findViewById(R.id.btn1) as Button
    }

    override fun initEvent(savedInstanceState: Bundle?) {

    }

    override fun initViewListener() {
        btn?.setOnClickListener {
            requestNetwork()
        }
    }

    //带进度框的网络请求
    private fun requestNetwork() {

        val progressSubscriber = object : ProgressSubscriber<UserInfoVo>(this@MainActivity, "登录中...") {
            override fun _onNext(retData: UserInfoVo?) {
                Toast.makeText(this@MainActivity, "登录成功!!!", Toast.LENGTH_SHORT).show()
            }
        }
        HttpUtil.instance.userLogin("lisi", "123456", this, progressSubscriber)
    }
}
