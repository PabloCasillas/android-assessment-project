package com.vp.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.favorites_db.MoviesDao
import com.vp.favorites_db.entities.MovieFavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel @Inject constructor(
    private val moviesDao: MoviesDao
) : ViewModel(), CoroutineScope {

    private val pendingJobs = Job()
    private val _moviesFavorites: MutableLiveData<List<MovieFavoriteEntity>> = MutableLiveData()
    val moviesFavorites: LiveData<List<MovieFavoriteEntity>> = _moviesFavorites

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + pendingJobs

    fun getFavoritesMovies() {
        launch {
            _moviesFavorites.postValue(moviesDao.getMoviesFavorites())
        }
    }

    override fun onCleared() {
        pendingJobs.cancel()
    }
}