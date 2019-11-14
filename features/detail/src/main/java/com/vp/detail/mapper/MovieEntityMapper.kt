package com.vp.detail.mapper

import com.vp.detail.model.MovieDetail
import com.vp.favorites_db.entities.MovieFavoriteEntity
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() : Mapper<MovieDetail, MovieFavoriteEntity> {
    override fun map(input: MovieDetail): MovieFavoriteEntity =
        MovieFavoriteEntity(
            imdbID = input.imdbID,
            title = input.title,
            year = input.year,
            runtime = input.runtime,
            poster = input.poster,
            plot = input.plot,
            director = input.director
        )
}