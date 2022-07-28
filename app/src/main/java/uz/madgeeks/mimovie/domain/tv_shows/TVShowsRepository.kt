package uz.madgeeks.mimovie.domain.tv_shows

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResponse

interface TVShowsRepository {
    suspend fun getAllTopRatedTVShows(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Flow<BaseNetworkResult<TVShowsResponse>>

    suspend fun getPopularTVShows(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Flow<BaseNetworkResult<TVShowsResponse>>

    suspend fun getOnTheAirTVShows(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Flow<BaseNetworkResult<TVShowsResponse>>

    suspend fun getSearchedTVShows(query: String): Flow<BaseNetworkResult<TVShowsResponse>>
}