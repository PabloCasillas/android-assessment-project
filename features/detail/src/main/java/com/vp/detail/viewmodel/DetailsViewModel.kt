package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.detail.DetailActivity
import com.vp.detail.mapper.MovieEntityMapper
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import com.vp.favorites_db.MoviesDao
import com.vp.favorites_db.entities.MovieFavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback
import kotlin.coroutines.CoroutineContext

class DetailsViewModel @Inject constructor(
    private val detailService: DetailService,
    private val moviesDao: MoviesDao,
    private val movieEntityMapper: MovieEntityMapper
) : ViewModel(), CoroutineScope {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private val pendingJobs = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + pendingJobs

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(DetailActivity.queryProvider.getMovieId())
            .enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
                override fun onResponse(
                    call: Call<MovieDetail>?,
                    response: Response<MovieDetail>?
                ) {
                    details.postValue(response?.body())
                    response?.body()?.title?.let {
                        title.postValue(it)
                    }

                    loadingState.value = LoadingState.LOADED
                }

                override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                    details.postValue(null)
                    loadingState.value = LoadingState.ERROR
                }
            })
    }

    fun addMovieToFavorites() {
        details.value?.let {
            val movieEntity = movieEntityMapper.map(it)
            launch {
                moviesDao.insertMovieFavorite(movieEntity)
            }
        }
    }

    override fun onCleared() {
        pendingJobs.cancel()
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}