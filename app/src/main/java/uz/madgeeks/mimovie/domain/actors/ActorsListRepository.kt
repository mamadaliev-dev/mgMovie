package uz.madgeeks.mimovie.domain.actors

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.actors.model.remote.ActorsResponse
import uz.madgeeks.mimovie.data.base.BaseNetworkResult

interface ActorsListRepository {
    suspend fun getAllWeeklyFamousPersons(): Flow<BaseNetworkResult<ActorsResponse>>
    suspend fun getAllDailyFamousPersons(): Flow<BaseNetworkResult<ActorsResponse>>
    suspend fun getSearchedActors(query: String): Flow<BaseNetworkResult<ActorsResponse>>
}