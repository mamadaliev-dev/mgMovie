package uz.madgeeks.mimovie.domain.search

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val homeRepository: SearchRepository) {
    suspend fun getSearchMovies(query: String): Flow<BaseNetworkResult<MoviesResponse>> {
        return homeRepository.getSearchMovies(query)
    }
}