package uz.madgeeks.mimovie.domain.genre

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.data.genre.remote.genres.MoviesGenresResponse
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResponse
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResult

interface GenreRepository {
    suspend fun getAllMoviesGenres(): Flow<BaseNetworkResult<MoviesGenresResponse>>

    suspend fun getAllTVShowGenres(): Flow<BaseNetworkResult<MoviesGenresResponse>>

    suspend fun getMoviesByGenre(id: Int): Flow<BaseNetworkResult<List<MovieResultDto>>>

    suspend fun getTVShowsByGenre(id: Int): Flow<BaseNetworkResult<TVShowsResponse>>
}