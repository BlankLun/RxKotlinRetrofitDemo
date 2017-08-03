package com.lkl.demo.https

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.lkl.networklibrary.R

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      带加载进度框的订阅者
 */
abstract class ProgressSubscriber<T>(context: Context, msg: String) : HttpSimpleSubscriber<T>() {

    private var mDialog: Dialog? = null

    init {
        mDialog = createProgressDialog(context, msg)
    }

    override fun onComplete() {
        dismissProgressDialog()
        Log.d(TAG, "onComplete")
        _onComplete()
    }

    override fun onError(t: Throwable) {
        dismissProgressDialog()
        Log.d(TAG, "onError:" + t)
        super.onError(t)
    }

    /**
     * 显示Dialog
     */
    fun showProgressDialog() {
        Log.d(TAG, "showProgressDialog")
        mDialog?.show()
    }

    /**
     * 隐藏Dialog
     */
    private fun dismissProgressDialog() {
        Log.d(TAG, "dismissProgressDialog")
        mDialog?.dismiss()
        mDialog = null
    }

    companion object {
        private val TAG = "ProgressSubscriber"

        private fun createProgressDialog(context: Context, msg: String): Dialog {
            val inflater = LayoutInflater.from(context)
            val v = inflater.inflate(R.layout.dialog_loading_layout, null)// 得到加载view
            val layout = v
                    .findViewById(R.id.dialog_loading_view) as LinearLayout// 加载布局
            val tipTextView = v.findViewById(R.id.tipTextView) as TextView// 提示文字
            tipTextView.text = msg// 设置加载信息

            val loadingDialog = Dialog(context, R.style.LoadingDialogStyle)// 创建自定义样式dialog
            loadingDialog.setCancelable(true) // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false) // 点击加载框以外的区域
            loadingDialog.setContentView(layout, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT))// 设置布局
            /**
             * 将显示Dialog的方法封装在这里面
             */
            val window = loadingDialog.window
            val lp = window?.attributes
            lp?.width = WindowManager.LayoutParams.MATCH_PARENT
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
            window?.setGravity(Gravity.CENTER)
            window?.attributes = lp
            window?.setWindowAnimations(R.style.LoadingDialogAnimStyle)

            return loadingDialog
        }
    }
}
