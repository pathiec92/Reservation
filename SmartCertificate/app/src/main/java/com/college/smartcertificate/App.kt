package com.college.smartcertificate

import android.app.Application
//import androidx.room.Room
//import com.blue.goeat.data.dao.AppDatabase
//import com.college.nfcreadwrite.di.InjectorUtils.provideContext
import com.college.smartcertificate.di.InjectorUtils.provideContext
//import com.facebook.stetho.Stetho

class App : Application() {
   // lateinit var appDatabase: AppDatabase
    override fun onCreate() {
        super.onCreate()
       // Stetho.initializeWithDefaults(this)
        provideContext = this
        /*appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "smart-cert-db"
        ).fallbackToDestructiveMigration().build()
*/
    }

}