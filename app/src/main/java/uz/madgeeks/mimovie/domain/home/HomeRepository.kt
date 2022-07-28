package uz.madgeeks.mimovie.domain.home

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto

interface HomeRepository {
    suspend fun getAllNewMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>>

    suspend fun getAllPopularMovies() : Flow<BaseNetworkResult<List<MovieResultDto>>>

    suspend fun getAllTopRatedMovies() : Flow<BaseNetworkResult<List<MovieResultDto>>>

    suspend fun getAllUpcomingMovies() : Flow<BaseNetworkResult<List<MovieResultDto>>>


//    suspend fun getAllFamousPersons() : Flow<BaseNetworkResult<ActorsResponse>>

//    suspend fun getDailyPopularTvShows() : Flow<BaseNetworkResult<List<MovieResultDto>>>

}