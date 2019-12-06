package com.college.smartcertificate.di


import com.college.smartcertificate.ui.dashboard.DashboardViewModelFactory
import com.college.smartcertificate.ui.home.HomeViewModelFactory

object InjectorUtils {
    //lateinit var provideContext: App

   // fun provideAppDb() = provideContext.appDatabase

    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }

    fun provideDashboardViewModelFactory(): DashboardViewModelFactory {
        return DashboardViewModelFactory()
    }


}