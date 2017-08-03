package com.lkl.demo.https.exception

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      Token失效异常
 */
class ExpiredTokenException internal constructor(message: String) : RuntimeException()