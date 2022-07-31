package uz.madgeeks.mimovie.domain.season_details

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieDetailResponse
import uz.madgeeks.mimovie.data.season_details.models.SeasonDetails

interface SeasonDetailRepository {
    suspend fun getSeasonDetailById(tv_id : Long, season_number : Int): Flow<BaseNetworkResult<SeasonDetails>>
}