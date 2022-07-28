package uz.madgeeks.mimovie.data.tv_show_details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.tv_show_details.models.TVShowDetails
import uz.madgeeks.mimovie.domain.tv_show_details.TVShowDetailRepository
import javax.inject.Inject

class TVShowDetailRepositoryImpl @Inject constructor(private val service: TVShowDetailService) :
    TVShowDetailRepository {
    override suspend fun getTVShowDetailById(id: Long): Flow<BaseNetworkResult<TVShowDetails>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getTVShowDetailById(id)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getTVTrailerListById(id: Long): Flow<BaseNetworkResult<MovieTrailerRes>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getTVTrailerListById(id)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body() ?: MovieTrailerRes(0, null)))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

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
}