package com.blue.goeat.ui.main.ui.di

import com.blue.goeat.ui.main.ui.account.AccountViewModelFactory
import com.blue.goeat.ui.main.ui.home.HomeViewModelFactory

object InjectorUtils {
    fun provideHomeViewModelFactory():HomeViewModelFactory{
        return HomeViewModelFactory()
    }

    fun provideAccountViewModelFactory(): AccountViewModelFactory {
        return AccountViewModelFactory()
    }
}