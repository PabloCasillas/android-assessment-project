package com.example.navigation

import android.content.Intent

interface Navigator {
    fun getDetailIntent(imdbID: String): Intent
    fun getFavoritesIntent(imdbID: String): Intent
}