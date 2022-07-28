package uz.madgeeks.mimovie.domain.movie_detail

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.*
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val homeRepository: MovieDetailRepository) {
    suspend fun getMovieDetailById(id : Long): Flow<BaseNetworkResult<MovieDetailResponse>> {
        return homeRepository.getMovieDetailById(id)
    }

    suspend fun getMovieTrailerListById(id : Long): Flow<BaseNetworkResult<MovieTrailerRes>> {
        return homeRepository.getMovieTrailerListById(id)
    }

//    suspend fun getReviews(id: Long): Flow<BaseNetworkResult<List<Review>>> {
//        return homeRepository.getReviews(id)
//    }

    suspend fun getCredits(id: Long): Flow<BaseNetworkResult<Credits>> {
        return homeRepository.getCredits(id)
    }
}