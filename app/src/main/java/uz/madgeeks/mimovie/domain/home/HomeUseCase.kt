package uz.madgeeks.mimovie.domain.home

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend fun getAllNewMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return homeRepository.getAllNewMovies()
    }

    suspend fun getAllPopularMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return homeRepository.getAllPopularMovies()
    }

    suspend fun getAllTopRatedMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return homeRepository.getAllTopRatedMovies()
    }

    suspend fun getAllUpcomingMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return homeRepository.getAllUpcomingMovies()
    }

//    suspend fun getAllFamousPersons(): Flow<BaseNetworkResult<ActorsResponse>> {
//        return homeRepository.getAllFamousPersons()
//    }

//    suspend fun getDailyPopularTvShows(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
//        return homeRepository.getDailyPopularTvShows()
//    }

}