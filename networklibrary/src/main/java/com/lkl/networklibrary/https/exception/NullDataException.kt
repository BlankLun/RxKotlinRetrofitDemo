package com.lkl.demo.https.exception

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      空数据异常
 */
class NullDataException internal constructor(message: String) : RuntimeException(message)