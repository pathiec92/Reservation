package com.college.smartcertificate.data

data class Grade(
    var sem1 : ArrayList<Sem> = ArrayList() ,
    var sem2 : ArrayList<Sem> = ArrayList(),
    var sem3 : ArrayList<Sem> = ArrayList(),
    var sem4 : ArrayList<Sem> = ArrayList(),
    var sem5 : ArrayList<Sem> = ArrayList(),
    var sem6 : ArrayList<Sem> = ArrayList(),
    var sem7 : ArrayList<Sem> = ArrayList(),
    var sem8 : ArrayList<Sem> = ArrayList(),
    var sem9 : ArrayList<Sem> = ArrayList(),
    var sem10 : ArrayList<Sem> = ArrayList()
)

data class Sem(
    var cr : String = "",
    var grd : String = ""
)