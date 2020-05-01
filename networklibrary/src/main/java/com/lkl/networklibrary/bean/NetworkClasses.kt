package com.lkl.networklibrary.bean

/**
 * Created    likunlun
 * Time       2017/8/3 12:02
 * Desc	      desc
 */
data class ResultData<T>(var code: Int, var message: String, var data: T?)

data class UserInfoVo(var userName: String, var password: String, var nickName: String)