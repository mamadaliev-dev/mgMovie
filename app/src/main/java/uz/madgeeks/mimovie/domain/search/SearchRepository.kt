package uz.madgeeks.mimovie.domain.search

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse

interface SearchRepository {
    suspend fun getSearchMovies(
        query: String
    ): Flow<BaseNetworkResult<MoviesResponse>>
}