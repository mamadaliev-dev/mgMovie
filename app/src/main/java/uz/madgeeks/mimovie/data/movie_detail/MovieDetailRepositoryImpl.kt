package uz.madgeeks.mimovie.data.movie_detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.movie_detail.model.MovieDetailService
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieDetailResponse
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Review
import uz.madgeeks.mimovie.domain.movie_detail.MovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val service: MovieDetailService) :
    MovieDetailRepository {

    override suspend fun getMovieDetailById(id: Long): Flow<BaseNetworkResult<MovieDetailResponse>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getMovieDetailById(id)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getMovieTrailerListById(id: Long): Flow<BaseNetworkResult<MovieTrailerRes>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getMovieTrailerListById(id)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body() ?: MovieTrailerRes(0, null)))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

//    override suspend fun getReviews(id: Long): Flow<BaseNetworkResult<List<Review>>> {
//        return flow {
//            emit(BaseNetworkResult.Loading(true))
//            val response = service.getReviews(id)
//            emit(BaseNetworkResult.Loading(false))
//            if (response.code() == 200) {
//                emit(BaseNetworkResult.Success(response.body()!!))
//            } else {
//                emit(BaseNetworkResult.Error("Xatolik"))
//            }
//        }
//    }

    override suspend fun getCredits(id: Long): Flow<BaseNetworkResult<Credits>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getCredits(id)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body() ?: Credits(0, null,null)))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

//    override fun getSimilarMovies(id: Long): LiveData<BaseNetworkResult<MoviesResponse>> {
//        val response = MutableLiveData<BaseNetworkResult<MoviesResponse>>()
//        response.value = BaseNetworkResult.Loading(true)
//        service.getSimilarMovies(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { res ->
//                    response.value = BaseNetworkResult.Loading(false)
//                    response.value = BaseNetworkResult.Success(res)
//                },
//                {
//                    Log.d("DDDD", "getCompanies: XATO")
//                    response.value = BaseNetworkResult.Loading(false)
//                    response.value = BaseNetworkResult.Error("xatolik")
//                }
//            )
//
//        return response
//    }
}