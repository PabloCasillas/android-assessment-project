package com.vp.movies.di

import android.app.Application
import com.vp.detail.di.DetailActivityModule
import com.vp.favorites_db.di.DatabaseModule
import com.vp.list.di.MovieListActivityModule
import com.vp.movies.MoviesApplication
import com.vp.navigation.di.NavigationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, AndroidSupportInjectionModule::class, NetworkModule::class,
        MovieListActivityModule::class, DetailActivityModule::class, NavigationModule::class, DatabaseModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesApplication)
}