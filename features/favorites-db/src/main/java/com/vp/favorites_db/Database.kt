package com.vp.favorites_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vp.favorites_db.entities.MovieFavoriteEntity

@Database(entities = [MovieFavoriteEntity::class], version = DataBase.VERSION)
abstract class DataBase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun moviesDao(): MoviesDao
}