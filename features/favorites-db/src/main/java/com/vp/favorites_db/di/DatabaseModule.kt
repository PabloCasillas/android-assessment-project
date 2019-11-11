package com.vp.favorites_db.di

import android.app.Application
import androidx.room.Room
import com.vp.favorites_db.DataBase
import com.vp.favorites_db.MoviesDao
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DatabaseModule {
    @Singleton
    @Binds
    fun roomDataBase(application: Application): DataBase =
        Room.databaseBuilder(application, DataBase::class.java, "db").build()

    @Singleton
    @Binds
    fun moviesDao(dataBase: DataBase): MoviesDao =
        dataBase.moviesDao()
}
