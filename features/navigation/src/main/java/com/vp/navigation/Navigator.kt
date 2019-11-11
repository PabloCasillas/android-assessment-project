package com.vp.navigation

import android.content.Intent

interface Navigator {
    fun getDetailIntent(imdbID: String): Intent
    fun getFavoritesIntent(): Intent
}