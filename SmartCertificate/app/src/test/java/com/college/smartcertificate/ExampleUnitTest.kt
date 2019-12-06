package com.college.smartcertificate

import com.college.smartcertificate.data.StudentEntity
import com.college.smartcertificate.data.YearWise
import com.college.smartcertificate.util.EnglishNumberToWords
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        val s = "2"
       println(s.toFloat().toInt().toString())
        println(s.toFloat().toInt().toString().toInt())
       // println(EnglishNumberToWords().convert(s.toFloat().toInt().toString().toInt().toLong()))
    }
@Test
fun parseSem(){
    val data = "papcode/Grades:-ARCH101/A,ARCH102/O,ARCH103/E,ARCH104/A,ARCH105/A,ARCH106/C,ARCH181/E,ARCH182/O,ARCH183/E".split(":-")[1]
    for ((index, s) in data.split(",").withIndex()) {
        println("index ${index}, s = $s")
    }
}
    @Test
    fun parseTest() {
        val str =
            "SHREYA DATTA\tSubrata Datta\t142750310032\t2014-2015\tFIRST\tGOLD\tBachelor of Architecture      \t\t\t2019\t1\t142750310032\t8.47\t2015\t8.90\t2015\t8.62\t9.14\t2016\t8.93\t2016\t9.03\t9.00\t2017\t8.20\t2017\t8.60\t9.00\t2018\t8.4\t2018\t8.7\t10\t2019\t8.2\t2019\t9.1\tP\t190\t8.81\t1\tpapcode/Grades:-ARCH101/A,ARCH102/O,ARCH103/E,ARCH104/A,ARCH105/A,ARCH106/C,ARCH181/E,ARCH182/O,ARCH183/E\tpapcode/Grades:-ARCH1001C/A,ARCH1002B/E,ARCH1003B/E,ARCH1081/A\tpapcode/Grades:-ARCH201/E,ARCH202/O,ARCH203/A,ARCH204/E,ARCH205/A,ARCH281/E,ARCH282/A,ARCH283/O,ARCH284/E\tpapcode/Grades:-ARCH301/O,ARCH302/O,ARCH303/B,ARCH304/B,ARCH305/E,ARCH306/E,ARCH381/O,ARCH382/O,ARCH383/O\tpapcode/Grades:-ARCH401/O,ARCH402/E,ARCH403/A,ARCH404/E,ARCH481/A,ARCH482/O,ARCH483/A,ARCH484/O,ARCH485/E\tpapcode/Grades:-ARCH501/E,ARCH502/O,ARCH503/E,ARCH504/A,ARCH505/O,ARCH506/O,ARCH581/A,ARCH582/E,ARCH583/A,ARCH584/O\tpapcode/Grades:-ARCH601/A,ARCH602/A,ARCH603/A,ARCH604/A,ARCH605/E,ARCH681/B,ARCH682/E,ARCH683/O,ARCH684/A\tpapcode/Grades:-ARCH701/E,ARCH702/A,ARCH703/E,ARCH704/E,ARCH705/O,ARCH706/E,ARCH781/E,ARCH782/E\tpapcode/Grades:-ARCH801/A,ARCH802/B,ARCH803/A,ARCH804/E,ARCH805/E,ARCH806/O,ARCH881/A,ARCH882/E\tpapcode/Grades:-ARCH981/O\n"
        var c = 0
        for (s in str.split("\t")) {
            println("index ${c}, s = $s")
            val student = StudentEntity()
            val sem1 = YearWise(sem = "1")
            student.yearWise.add(sem1)
            val sem2 = YearWise(sem = "2")
            student.yearWise.add(sem2)

            val sem3 = YearWise(sem = "3")
            student.yearWise.add(sem3)

            val sem4 = YearWise(sem = "4")
            student.yearWise.add(sem4)

            val sem5 = YearWise(sem = "5")
            student.yearWise.add(sem5)

            val sem6 = YearWise(sem = "6")
            student.yearWise.add(sem6)

            val sem7 = YearWise(sem = "7")
            student.yearWise.add(sem7)

            val sem8 = YearWise(sem = "8")
            student.yearWise.add(sem8)

            val sem9 = YearWise(sem = "9")
            student.yearWise.add(sem9)

            val sem10 = YearWise(sem = "10")
            student.yearWise.add(sem10)

            when (c) {
                0 -> student.courseshortname = s
                1 -> student.regno = s
                2 -> student.regses = s
                3 -> student.colg = s
                4 -> student.colgname = s
                5 -> student.rollno = s
                6 -> student.name = s
                7 -> student.fname = s
                8 -> sem1.sgpa = s
                9 -> sem1.year = s
                10 -> sem2.sgpa = s
                11 -> sem2.year = s
                12 -> student.ygpa1 = s
                13 -> sem3.sgpa = s
                14 -> sem3.year = s
                15 -> sem4.sgpa = s
                16 -> sem4.year = s
                17 -> student.ygpa2 = s
                18 -> sem5.sgpa = s
                19 -> sem5.year = s
                20 -> sem6.sgpa = s
                21 -> sem6.year = s
                22 -> student.ygpa3 = s
                23 -> sem7.sgpa = s
                24 -> sem7.year = s
                25 -> sem8.sgpa = s
                26 -> sem8.year = s
                27 -> student.ygpa4 = s
                28 -> sem9.sgpa = s
                29 -> sem9.year = s
                30 -> sem10.sgpa = s
                31 -> sem10.year = s
                32 -> student.ygpa5 = s
                33 -> student.result = s
                34 -> student.cRS = s
                35 -> student.dGPA = s
                36 -> student.rANK = s
            }
            c++

        }
    }
}
