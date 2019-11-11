package com.vp.navigation

import android.content.Intent
import android.net.Uri
import com.vp.navigation.ext.toViewIntent
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    override fun getDetailIntent(imdbID: String): Intent =
        Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)
            .appendPath(DETAIL_PREFIX)
            .appendQueryParameter(QUERY_PARAMETER_IMDBID, imdbID)
            .toViewIntent()

    override fun getFavoritesIntent(): Intent =
        Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)
            .appendPath(FAVORITES_PREFIX)
            .toViewIntent()

    companion object {
        private const val HOST = "movies"
        private const val DETAIL_PREFIX = "detail"
        private const val FAVORITES_PREFIX = "favorites"
        private const val SCHEME = "app"
        private const val QUERY_PARAMETER_IMDBID = "imdbID"
    }
}