package uz.madgeeks.mimovie.domain.actors

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.mimovie.data.actors.model.local.ActorsResultDto
import uz.madgeeks.mimovie.data.actors.model.remote.movie_credits.ActorCreditsInMovies
import uz.madgeeks.mimovie.data.actors.model.remote.tv_credits.ActorCreditsInTVShows
import uz.madgeeks.mimovie.data.base.BaseNetworkResult

interface ActorsRepository {
    suspend fun getPersonDetailById(id : Long) : Flow<BaseNetworkResult<ActorsResultDto>>

    suspend fun getMovieCreditsById(id : Long) : Flow<BaseNetworkResult<ActorCreditsInMovies>>

    suspend fun getTVCreditsById(id : Long) : Flow<BaseNetworkResult<ActorCreditsInTVShows>>
}