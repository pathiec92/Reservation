package com.college.smartcertificate.data

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Encryption {
    private val key = android.util.Base64.decode("+mJEopekugMuid6bd/Oi+Q==", 0)
    fun encrypt(plainText: String): String {
        return String(
            android.util.Base64.encode(
                encrypt(key, plainText.toByteArray()), 0
            )
        )
    }

    fun decrypt(base64EncodedData:String):String{
        return String(
            decrypt(key,android.util.Base64.decode(base64EncodedData, 0))
        )
    }

    @Throws(Exception::class)
    private fun encrypt(raw: ByteArray, clear: ByteArray): ByteArray {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    private fun decrypt(raw: ByteArray, encrypted: ByteArray): ByteArray {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return cipher.doFinal(encrypted)
    }
}