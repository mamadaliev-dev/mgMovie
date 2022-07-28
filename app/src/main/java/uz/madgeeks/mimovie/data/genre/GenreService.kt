package uz.madgeeks.mimovie.data.genre

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.data.genre.remote.genres.MoviesGenresResponse
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResponse

interface GenreService {
    @GET("genre/movie/list")
    suspend fun getAllMoviesGenres(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesGenresResponse>

    @GET("genre/tv/list")
    suspend fun getAllTVShowGenres(
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesGenresResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<MoviesResponse>

    @GET("discover/tv")
    suspend fun getTVShowsByGenre(
        @Query("with_genres") genreId: Int,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<TVShowsResponse>
}