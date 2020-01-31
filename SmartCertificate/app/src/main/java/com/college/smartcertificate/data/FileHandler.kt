package com.college.smartcertificate.data

import com.college.smartcertificate.data.StudentData.nfcId
import com.college.smartcertificate.di.InjectorUtils
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter

suspend fun write(msg:String){
    try {
        FileWriter("${InjectorUtils.sdCardPath()}/verified.csv", true).use { fw ->
            BufferedWriter(fw).use { bw ->
                PrintWriter(bw).use { out ->
                    out.println("${System.currentTimeMillis()},$nfcId,$msg")
                }
            }
        }
    } catch (e: Exception) {
      e.printStackTrace()
    }

}