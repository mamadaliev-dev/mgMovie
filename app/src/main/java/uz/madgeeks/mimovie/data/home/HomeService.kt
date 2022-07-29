package uz.madgeeks.mimovie.data.home

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse

interface HomeService {
    @GET("movie/now_playing")
    suspend fun getAllNewMovies(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getAllTopRatedMovies(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getAllUpcomingMovies(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesResponse>
}