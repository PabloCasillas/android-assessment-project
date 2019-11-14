package com.vp.favorites_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vp.favorites_db.entities.MovieFavoriteEntity

@Dao
interface MoviesDao {
    @Query("SELECT * FROM MovieFavoriteEntity")
    suspend fun getMoviesFavorites(): List<MovieFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieFavorite(vararg movieFavoriteEntity: MovieFavoriteEntity)

    @Delete
    suspend fun deleteMovieFavorite(vararg movieFavoriteEntity: MovieFavoriteEntity)
}