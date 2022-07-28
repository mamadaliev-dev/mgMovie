package uz.madgeeks.mimovie.domain.movie_detail

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.*

interface MovieDetailRepository {
    suspend fun getMovieDetailById(id : Long): Flow<BaseNetworkResult<MovieDetailResponse>>

    suspend fun getMovieTrailerListById(id : Long): Flow<BaseNetworkResult<MovieTrailerRes>>

//    suspend fun getReviews(id : Long): Flow<BaseNetworkResult<List<Review>>>

    suspend fun getCredits(id : Long): Flow<BaseNetworkResult<Credits>>
}