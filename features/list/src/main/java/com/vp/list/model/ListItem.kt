package com.vp.list.model

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

@Keep
data class ListItem(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String
)
