package uz.madgeeks.mimovie.data.movie_detail.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieDetailResponse
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Review

interface MovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetailById(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailerListById(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MovieTrailerRes>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<List<Review>>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<Credits>
}