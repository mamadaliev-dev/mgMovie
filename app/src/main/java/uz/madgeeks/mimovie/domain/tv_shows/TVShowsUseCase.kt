package uz.madgeeks.mimovie.domain.tv_shows

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResponse
import uz.madgeeks.mimovie.domain.home.HomeRepository
import javax.inject.Inject

class TVShowsUseCase @Inject constructor(private val homeRepository: TVShowsRepository) {
    suspend fun getAllTopRatedTVShows(): Flow<BaseNetworkResult<TVShowsResponse>> {
        return homeRepository.getAllTopRatedTVShows()
    }

    suspend fun getPopularTVShows(): Flow<BaseNetworkResult<TVShowsResponse>> {
        return homeRepository.getPopularTVShows()
    }

    suspend fun getOnTheAirTVShows(): Flow<BaseNetworkResult<TVShowsResponse>> {
        return homeRepository.getOnTheAirTVShows()
    }

    suspend fun getSearchedTVShows(query: String): Flow<BaseNetworkResult<TVShowsResponse>> {
        return homeRepository.getSearchedTVShows(query)
    }
}