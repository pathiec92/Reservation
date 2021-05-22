package com.blue.goeat.di

import com.blue.goeat.App
import com.blue.goeat.ui.main.ui.account.AccountViewModelFactory
import com.blue.goeat.ui.main.ui.home.HomeViewModelFactory
import com.blue.goeat.ui.main.ui.order.OrderViewModelFactory
import com.blue.goeat.ui.main.ui.wizard.CollegeWizViewModelFactory

object InjectorUtils {
    lateinit var provideContext: App

    fun provideAppDb() = provideContext.appDatabase

    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }

    fun provideOrderViewModelFactory(): OrderViewModelFactory {
        return OrderViewModelFactory()
    }

    fun provideCollegeWizViewModelFactory(): CollegeWizViewModelFactory {
        return CollegeWizViewModelFactory()
    }

    fun provideAccountViewModelFactory(): AccountViewModelFactory {
        return AccountViewModelFactory()
    }
}