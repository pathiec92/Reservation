package com.college.smartcertificate.data

import android.util.Base64
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Signer {
    val publicKeyStr =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk48GNC3Svn7D3Eqqnc0qUkU+KFHpCq7emOlGQbhmKqsRNAA8x1CWa5gkmktJqnBeXHDfO0q9tcCPLCvUL9MNPSeKqe3XjHmYRavV3H5vHhEGhHSs703VJXgYLbblgND7JHs4X9xF1sd8LiLnDbQ00V2uDNdt1a4EyOJ2O2V5GIxHuSMcBCvipbQw+UXnIP7mlfzMB0P1tMOLoLOh3ia2LQc1biTmdBylbEBbfmsItvUcuGNl11S8cdFpR11JAINLODa8TsouNmWqRjE+XgbwBJJIr1EZZiMc8sM7FBSpV8VuqCGoSjPv5EbOC7BOizJQNuczQCnlmqv/GAqax6PyfQIDAQAB"

    fun decrypt(data:String):String?{
        val dataDecoded = Base64.decode(data,0)
        val signature = dataDecoded.slice(0 until 256)
        val payload = dataDecoded.slice(256 until dataDecoded.size)
        val isValid =verify(payload.toByteArray(),signature.toByteArray())
        print("Data isValid $isValid ")
        val record = String(payload.toByteArray())
        print("Data received from client $record")
        return if(isValid)record else null
    }
    fun verify(buf: ByteArray, signature: ByteArray): Boolean {
        val publickeybytes = Base64.decode(publicKeyStr,0)

        val ks_public = X509EncodedKeySpec(publickeybytes)
        val kf_public = KeyFactory.getInstance("RSA")
        val pub = kf_public.generatePublic(ks_public)

        val sign = Signature.getInstance("SHA256withRSA")
        sign.initVerify(pub)

       // val buf = data.toByteArray()
        sign.update(buf, 0, buf.size)

        val isVerified = sign.verify(signature)
        println(
            if (isVerified)
                "Genuine Ceritificate"
            else
                "Certificate Tampered"
        )
        return isVerified
    }
}