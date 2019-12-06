package com.college.smartcertificate.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.smartcertificate.data.StudentData.notify
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.util.EnglishNumberToWords

class HomeViewModel : ViewModel() {

    var studentName: ObservableField<String> = ObservableField(studentEntity.name)
    val regNum: ObservableField<String> = ObservableField(studentEntity.regno)
    val medal: ObservableField<String> = ObservableField("(${studentEntity.medal} Medal)")
    val courseName: ObservableField<String> = ObservableField(studentEntity.CourseLname)
    val rank: ObservableField<String> = ObservableField(getRank())

    val invalidCardDetected:MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        notify = {
            when(it){
                true -> invalidCardDetected.value = true
                false ->  refreshView()
            }
        }
    }


    private fun refreshView() {
        studentName.set(studentEntity.name)
        regNum.set(studentEntity.regno)
        medal.set("(${studentEntity.medal} Medal)")
        courseName.set(studentEntity.CourseLname)
        rank.set(getRank())
    }

    private fun getRank(): String {
        return try {
            when (studentEntity.rANK.toInt()) {
                1 -> "First"
                2 -> "Second"
                3 -> "Third"
                4 -> "Fourth"
                5 -> "Fifth"
                else -> "${EnglishNumberToWords().convert(studentEntity.rANK.toInt().toLong())}th"
            }
        } catch (e: Exception) {
            ""
        }
    }
}

