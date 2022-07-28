package uz.madgeeks.mimovie.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.remote.response.movie.MoviesResponse
import uz.madgeeks.mimovie.domain.search.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service: SearchService) :
    SearchRepository {

    override suspend fun getSearchMovies(
        query: String
    ): Flow<BaseNetworkResult<MoviesResponse>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response =
                service.getSearchMovies(query = query)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }
}