package com.vp.favorites_db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["title", "year", "director"])
data class MovieFavoriteEntity(
    val title: String,
    val year: String,
    val runtime: String,
    val director: String,
    val plot: String,
    val poster: String
)