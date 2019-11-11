package com.vp.navigation.di

import com.vp.navigation.Navigator
import com.vp.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class NavigationModule {
    @Singleton
    @Binds
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}