package com.vp.favorites.di

import com.vp.favorites.FavoritesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesActivityModule {
    @ContributesAndroidInjector(modules = [FavoritesViewModelModule::class])
    abstract fun bindDetailActivity(): FavoritesActivity
}