package uz.madgeeks.mimovie.domain.season_details

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.season_details.models.SeasonDetails
import javax.inject.Inject

class SeasonDetailUseCase @Inject constructor(private val homeRepository: SeasonDetailRepository) {
    suspend fun getSeasonDetailsById(
        tv_id: Long,
        season_number: Int,
    ): Flow<BaseNetworkResult<SeasonDetails>> {
        return homeRepository.getSeasonDetailById(tv_id, season_number)
    }
}