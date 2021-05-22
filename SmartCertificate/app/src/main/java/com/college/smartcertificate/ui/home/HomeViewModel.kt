package com.college.smartcertificate.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.smartcertificate.R
import com.college.smartcertificate.data.StudentData
import com.college.smartcertificate.data.StudentData.isPhd
import com.college.smartcertificate.data.StudentData.notify
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.data.write
import com.college.smartcertificate.di.InjectorUtils
import com.college.smartcertificate.util.EnglishNumberToWords
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var studentName: ObservableField<String> = ObservableField(studentEntity.name)
    val regNum: ObservableField<String> = ObservableField(studentEntity.regno)
    val medal: ObservableField<String> = ObservableField("(${studentEntity.medal} Medal)")
    val courseName: ObservableField<String> = ObservableField(studentEntity.CourseLname)
    val rank: ObservableField<String> = ObservableField(getRank())

    val invalidCardDetected: MutableLiveData<Boolean> = MutableLiveData(false)


    var phStudentName: ObservableField<String> = ObservableField(StudentData.phdStudent.name)
    var phCertDate: ObservableField<String> = ObservableField(
        String.format(
            InjectorUtils.provideContext.getString(
                R.string.student_date
            ), StudentData.phdStudent.phDConferredDate
        )
    )
    val phdRegNum: ObservableField<String> = ObservableField(StudentData.phdStudent.regNo)
    val phDiscipline: ObservableField<String> =
        ObservableField("(${StudentData.phdStudent.discipline} Medal)")
    val phdDegreeName: ObservableField<String> = ObservableField(StudentData.phdStudent.degree)
    val supervisor: ObservableField<String> = ObservableField(StudentData.phdStudent.supervisor)
    val coSupervisor: ObservableField<String> = ObservableField(StudentData.phdStudent.coSupervisor)
    val date: ObservableField<String> = ObservableField(StudentData.phdStudent.phDConferredDate)

    init {
        notify = {
            when (it) {
                true -> invalidCardDetected.value = true
                false -> refreshView()
            }
        }
    }


    private fun refreshView() {
        if (isPhd) {
            phStudentName.set(StudentData.phdStudent.name)
            phdRegNum.set(StudentData.phdStudent.regNo)
            phDiscipline.set(StudentData.phdStudent.discipline)
            phdDegreeName.set(StudentData.phdStudent.degree)
            supervisor.set(StudentData.phdStudent.supervisor)
            coSupervisor.set(StudentData.phdStudent.coSupervisor)
            date.set(StudentData.phdStudent.phDConferredDate)
            phCertDate.set(
            String.format(
                InjectorUtils.provideContext.getString(
                    R.string.student_date
                ), StudentData.phdStudent.phDConferredDate
            )
            )
            GlobalScope.launch {
                //type,name,degree,discipline,regno
                write("phd,${StudentData.phdStudent.name},${StudentData.phdStudent.degree},${StudentData.phdStudent.discipline},${StudentData.phdStudent.regNo}")
            }

        } else {
            studentName.set(studentEntity.name)
            regNum.set(studentEntity.regno)
            medal.set("(${studentEntity.medal} Medal)")
            courseName.set("${studentEntity.CourseFname} ${studentEntity.CourseMName} ${studentEntity.CourseLname}")
            rank.set(getRank())
            //type,name,course,regno
            GlobalScope.launch {
                //type,name,degree,discipline,regno
                write("degree,${StudentData.studentEntity.name},${studentEntity.CourseFname} ${studentEntity.CourseMName} ${studentEntity.CourseLname},${StudentData.phdStudent.discipline},${StudentData.studentEntity.regno}")
            }
        }
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

