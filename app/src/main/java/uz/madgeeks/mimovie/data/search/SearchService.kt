package uz.madgeeks.mimovie.data.search

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse

interface SearchService {
    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
        @Query("query") query: String
    ): Response<MoviesResponse>
}