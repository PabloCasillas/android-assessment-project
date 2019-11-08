package com.example.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class NavigatorImpl : Navigator {
    @Inject
    protected lateinit var context: Context

    override fun getDetailIntent(imdbID: String): Intent {
        val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)
            .appendPath(DETAIL_PREFIX)
        return Intent(Intent.ACTION_VIEW, Uri.parse(this.toString()))
    }

    override fun getFavoritesIntent(imdbID: String): Intent {
        val uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)
            .appendPath(FAVORITES_PREFIX)
        return Intent(Intent.ACTION_VIEW, Uri.parse(this.toString()))
    }

    companion object {
        private const val HOST = "movies"
        private const val DETAIL_PREFIX = "/detail"
        private const val FAVORITES_PREFIX = "/favorites"
        private const val SCHEME = "app"
    }
}