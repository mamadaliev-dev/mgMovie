package uz.madgeeks.mimovie.data.actors_list.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.actors.model.remote.ActorsResponse
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse

interface ActorsListService {

    @GET("trending/person/week")
    suspend fun getAllWeeklyFamousPersons(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<ActorsResponse>

    @GET("trending/person/day")
    suspend fun getAllDailyFamousPersons(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<ActorsResponse>

    @GET("search/person")
    suspend fun getSearchedActors(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
        @Query("query") query: String
    ): Response<ActorsResponse>
}