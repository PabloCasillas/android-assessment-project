package com.vp.favorites_db.di

import android.content.Context
import androidx.room.Room
import com.vp.favorites_db.DataBase
import com.vp.favorites_db.MoviesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun roomDataBase(context: Context): DataBase =
        Room.databaseBuilder(context, DataBase::class.java, "db").build()

    @Singleton
    @Provides
    fun moviesDao(dataBase: DataBase): MoviesDao =
        dataBase.moviesDao()
}
