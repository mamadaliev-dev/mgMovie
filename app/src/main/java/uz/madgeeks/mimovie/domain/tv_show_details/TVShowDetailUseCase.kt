package uz.madgeeks.mimovie.domain.tv_show_details

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.tv_show_details.models.TVShowDetails
import javax.inject.Inject

class TVShowDetailUseCase @Inject constructor(private val homeRepository: TVShowDetailRepository) {
    suspend fun getTVShowDetailById(id: Long): Flow<BaseNetworkResult<TVShowDetails>> {
        return homeRepository.getTVShowDetailById(id)
    }

    suspend fun getTVTrailerListById(id : Long): Flow<BaseNetworkResult<MovieTrailerRes>> {
        return homeRepository.getTVTrailerListById(id)
    }

    suspend fun getCredits(id: Long): Flow<BaseNetworkResult<Credits>> {
        return homeRepository.getCredits(id)
    }
}