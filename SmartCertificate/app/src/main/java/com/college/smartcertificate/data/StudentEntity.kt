package com.college.smartcertificate.data

import com.google.gson.annotations.SerializedName

data class PhdStudent(
    var regNo: String = "",
    var refNo: String = "",
    var name: String = "",
    var degree: String = "",
    var phDConferredDate: String = "",
    var discipline: String = "",
    var supervisor: String = "",
    var coSupervisor: String = ""
)

data class StudentEntity(
    @SerializedName("courseshortname") var courseshortname: String = "",
    @SerializedName("regno") var regno: String = "",
    @SerializedName("regses") var regses: String = "",
    @SerializedName("colg") var colg: String = "",
    @SerializedName("colgname") var colgname: String = "",
    @SerializedName("rollno") var rollno: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("fname") var fname: String = "",
    @SerializedName("ygpa1") var ygpa1: String = "",
    @SerializedName("ygpa2") var ygpa2: String = "",
    @SerializedName("ygpa3") var ygpa3: String = "",
    @SerializedName("ygpa4") var ygpa4: String = "",
    @SerializedName("ygpa5") var ygpa5: String = "",
    var year1: String = "",
    var year2: String = "",
    var year3: String = "",
    var year4: String = "",
    var year5: String = "",


    @SerializedName("result") var result: String = "",
    @SerializedName("CRS_GRP") var cRS: String = "",
    @SerializedName("DGPA") var dGPA: String = "",
    @SerializedName("RANK") var rANK: String = "",
    var rankStatus: String = "",
    var CourseFname: String = "",
    var CourseMName: String = "",
    var CourseLname: String = "",
    var year: String = "",
    var medal: String = "",
    @SerializedName("year_wise") var yearWise: ArrayList<YearWise> = ArrayList()

)

data class YearWise(
    @SerializedName("sem") var sem: String = "",
    @SerializedName("sgpa") var sgpa: String = "",
    @SerializedName("year") var year: String = ""
)

object StudentData {
    enum class Type {
        DEGREE,
        PHD
    }

    var notify: (Boolean) -> Unit = {}
    var refreshed: ((Boolean) -> Int)? = { 1 }
    lateinit var nfcId:String


    /*var studentEntity:StudentEntity = Gson().fromJson<StudentEntity>(studentJson, StudentEntity::class.java)
    var gradeData = Gson().fromJson<Grade>(gradeJson, Grade::class.java)*/
    var studentEntity: StudentEntity = StudentEntity()
    var phdStudent: PhdStudent = PhdStudent()
    var isPhd = false
    var isRestarted = false
    var gradeData = Grade()
    lateinit var currentType: Type
    fun refreshData(data: String) {
        val dataArray = data.split("$")
        if (dataArray.isEmpty()) {
            notify(true)
            println("Invalid card detected")
            return
        }
        //isRestarted = isRestartReq()
        isRestarted = true
        isPhd = dataArray.size < 11
        if (isPhd) {
            phdStudent = PhdStudent()
            for ((index, s) in dataArray.withIndex()) {
                parsePhdData(index, s)
            }
        } else {
            parseDegreeData(dataArray)
            println("$studentEntity")
        }
        notify(false)
        refreshed?.let { it(isRestarted) }
    }

    private fun parseDegreeData(dataArray: List<String>) {
        studentEntity = StudentEntity()
        studentEntity.yearWise.clear()
        val sem1 = YearWise(sem = "Sem 1")
        studentEntity.yearWise.add(sem1)
        val sem2 = YearWise(sem = "Sem 2")
        studentEntity.yearWise.add(sem2)

        val sem3 = YearWise(sem = "Sem 3")
        studentEntity.yearWise.add(sem3)

        val sem4 = YearWise(sem = "Sem 4")
        studentEntity.yearWise.add(sem4)

        val sem5 = YearWise(sem = "Sem 5")
        studentEntity.yearWise.add(sem5)

        val sem6 = YearWise(sem = "Sem 6")
        studentEntity.yearWise.add(sem6)

        val sem7 = YearWise(sem = "Sem 7")
        studentEntity.yearWise.add(sem7)

        val sem8 = YearWise(sem = "Sem 8")
        studentEntity.yearWise.add(sem8)

        val sem9 = YearWise(sem = "Sem 9")
        studentEntity.yearWise.add(sem9)

        val sem10 = YearWise(sem = "Sem 10")
        studentEntity.yearWise.add(sem10)


        gradeData = Grade()
        gradeData.sem1 = ArrayList()
        gradeData.sem2 = ArrayList()
        gradeData.sem3 = ArrayList()
        gradeData.sem4 = ArrayList()
        gradeData.sem5 = ArrayList()
        gradeData.sem6 = ArrayList()
        gradeData.sem7 = ArrayList()
        gradeData.sem8 = ArrayList()
        gradeData.sem9 = ArrayList()
        gradeData.sem10 = ArrayList()
        for ((index, s) in dataArray.withIndex()) {
            println("index ${index}, s = $s")
            when (index) {
                0 -> studentEntity.name = s
                1 -> studentEntity.fname = s
                2 -> studentEntity.regno = s
                3 -> studentEntity.regses = s
                4 -> studentEntity.rankStatus = s
                5 -> studentEntity.medal = s
                6 -> studentEntity.CourseFname = s
                7 -> studentEntity.CourseMName = s
                8 -> studentEntity.CourseLname = s
                9 -> studentEntity.year = s
                10 -> studentEntity.rANK = getRank(s)

                11 -> studentEntity.regno = s
                12 -> sem1.sgpa = s
                13 -> sem1.year = s
                14 -> sem2.sgpa = s
                15 -> {
                    sem2.year = s
                    studentEntity.year1 = s
                }
                16 -> studentEntity.ygpa1 = s
                17 -> sem3.sgpa = s
                18 -> sem3.year = s
                19 -> sem4.sgpa = s
                20 -> {
                    sem4.year = s
                    studentEntity.year2 = s
                }
                21 -> studentEntity.ygpa2 = s
                22 -> sem5.sgpa = s
                23 -> sem5.year = s
                24 -> sem6.sgpa = s
                25 -> {
                    sem6.year = s
                    studentEntity.year3 = s
                }
                26 -> studentEntity.ygpa3 = s
                27 -> sem7.sgpa = s
                28 -> sem7.year = s
                29 -> sem8.sgpa = s
                30 -> {
                    sem8.year = s
                    studentEntity.year4 = s
                }
                31 -> studentEntity.ygpa4 = s
                32 -> sem9.sgpa = s
                33 -> sem9.year = s
                34 -> sem10.sgpa = s
                35 -> {
                    sem10.year = s
                    studentEntity.year5 = s
                }
                36 -> studentEntity.ygpa5 = s
                37 -> studentEntity.result = s
                38 -> studentEntity.cRS = s
                39 -> studentEntity.dGPA = s
                40 -> studentEntity.rANK = getRank(s)
                41 -> gradeData.sem1 = getSemList(s)
                42 -> gradeData.sem2 = getSemList(s)
                43 -> gradeData.sem3 = getSemList(s)
                44 -> gradeData.sem4 = getSemList(s)
                45 -> gradeData.sem5 = getSemList(s)
                46 -> gradeData.sem6 = getSemList(s)
                47 -> gradeData.sem7 = getSemList(s)
                48 -> gradeData.sem8 = getSemList(s)
                49 -> gradeData.sem9 = getSemList(s)
                50 -> gradeData.sem10 = getSemList(s)
            }


        }
    }

    private fun parsePhdData(index: Int, s: String) {
        println("index ${index}, s = $s")
        when (index) {
            0 -> phdStudent.regNo = s
            1 -> phdStudent.refNo = s
            2 -> phdStudent.name = s
            3 -> phdStudent.degree = s
            4 -> phdStudent.discipline = s
            5 -> phdStudent.supervisor = s
            6 -> phdStudent.coSupervisor = s
            7 -> phdStudent.phDConferredDate = s

        }
    }

    private fun isRestartReq(): Boolean {
        var restartReq = ::currentType.isInitialized

        if (restartReq) {
            val prvType = currentType
            currentType = if (isPhd) Type.PHD else Type.DEGREE
            restartReq = prvType != currentType
        } else {
            restartReq = true
        }
        currentType = if (isPhd) Type.PHD else Type.DEGREE
        return restartReq
    }
}

private fun getRank(s: String): String {
    return try {
        s.toFloat().toInt().toString()
    } catch (ex: Exception) {
        "0"
    }
}

private fun getSemList(data: String): java.util.ArrayList<Sem> {
    val semList = ArrayList<Sem>()
    if (data.isBlank()) return semList
    for ((index, s) in data.split(":-")[1].split(",").withIndex()) {
        println("index ${index}, s = $s")
        val semData = s.split("/")
        semList.add(Sem(semData[0], semData[1]))
    }
    return semList
}
