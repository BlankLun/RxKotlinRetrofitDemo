package com.lkl.demo.https.util

import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created    likunlun
 * Time       2017/8/3 11:06
 * Desc	      MD5加密工具类
 */
object MD5Util {

    private val TAG = "MD5Util"

    /**
     * 获取MD5值

     * @param s
     * *
     * @return
     */
    @JvmStatic
    fun MD5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = MessageDigest.getInstance("MD5")

            digest.update(s.toByteArray(charset("UTF-8")))

            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            messageDigest.forEach {
                var h = Integer.toHexString((0xFF and it.toInt()))
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, e.message)
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, e.message)
        }

        return ""
    }
}


