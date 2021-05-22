package com.college.smartcertificate.di


import com.college.smartcertificate.App
import com.college.smartcertificate.ui.dashboard.DashboardViewModelFactory
import com.college.smartcertificate.ui.home.HomeViewModelFactory

object InjectorUtils {
    fun sdCardPath(): String = provideContext.getExternalFilesDir(null)?.absolutePath ?: ""
    lateinit var provideContext: App

    // fun provideAppDb() = provideContext.appDatabase
    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }

    fun provideDashboardViewModelFactory(): DashboardViewModelFactory {
        return DashboardViewModelFactory()
    }


}