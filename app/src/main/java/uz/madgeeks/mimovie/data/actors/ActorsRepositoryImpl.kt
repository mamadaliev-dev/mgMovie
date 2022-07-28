package uz.madgeeks.mimovie.data.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.mimovie.data.actors.model.ActorsService
import uz.madgeeks.mimovie.data.actors.model.local.ActorsResultDto
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.domain.actors.ActorsRepository
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor(private val service: ActorsService) :
    ActorsRepository {
    override suspend fun getPersonDetailById(id : Long): Flow<BaseNetworkResult<ActorsResultDto>> {
    return flow {
        emit(BaseNetworkResult.Loading(true))
        val response = service.getPersonDetailById(id)
        emit(BaseNetworkResult.Loading(false))
        if (response.code() == 200) {
            emit(BaseNetworkResult.Success(response.body()!!))
        } else {
            emit(BaseNetworkResult.Error("Xatolik"))
        }
    }
    }
}