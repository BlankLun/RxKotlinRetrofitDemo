package com.lkl.demo.https.exception

import com.lkl.demo.https.consts.HttpStatus

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      网络请求自定义异常
 */
class ApiException(detailMessage: String) : RuntimeException(detailMessage) {

    constructor(resultCode: Int) : this(getApiExceptionMessage(resultCode)) {
        this.errorCode = resultCode
    }

    var errorCode = HttpStatus.ERROR

    companion object {


        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，在显示给用户
         * @param code
         * *
         * @return
         */
        private fun getApiExceptionMessage(code: Int): String {
            return when (code) {
                HttpStatus.ERROR -> "请求失败，请稍后再试..."
                HttpStatus.SERVER_ERROR -> "服务器正忙，请稍候再试..."
                HttpStatus.SOCKET_TIMEOUT -> "网络连接超时，请稍后再试..."

                HttpStatus.AUTHENTICATION_FAILED -> "认证失败"
                HttpStatus.WRONG_USERNAME -> "用户名错误，请检查输入是否正确..."
                HttpStatus.WRONG_PASSWORD -> "密码错误，请检查输入是否正确..."

                HttpStatus.VERIFICATION_CODE_INVALID -> "验证码失效，请重新获取..."
                HttpStatus.VERIFICATION_CODE_ERROR -> "验证码错误"
                HttpStatus.PHONE_NOT_REGISTERED -> "该手机号码未注册"
                HttpStatus.PHONE_HAS_REGISTERED -> "该手机号码已经被注册了"
                else -> "请求失败，请稍后再试..."
            }
        }
    }
}
