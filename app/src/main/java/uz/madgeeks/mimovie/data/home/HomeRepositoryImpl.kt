package uz.madgeeks.mimovie.data.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.domain.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val service: HomeService) : HomeRepository {

    override suspend fun getAllNewMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getAllNewMovies()
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!.results.map {
                    MovieResultDto(
                        adult = it.adult,
                        title = it.title,
                        id = it.id,
                        backdropPath = it.backdropPath,
                        genreIds = it.genreIds,
                        originalLanguage = it.originalLanguage,
                        originalTitle = it.originalTitle,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        video = it.video,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getAllPopularMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getAllPopularMovies()
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!.results.map {
                    MovieResultDto(
                        adult = it.adult,
                        title = it.title,
                        id = it.id,
                        backdropPath = it.backdropPath,
                        genreIds = it.genreIds,
                        originalLanguage = it.originalLanguage,
                        originalTitle = it.originalTitle,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        video = it.video,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getAllTopRatedMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getAllTopRatedMovies()
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!.results.map {
                    MovieResultDto(
                        adult = it.adult,
                        title = it.title,
                        id = it.id,
                        backdropPath = it.backdropPath,
                        genreIds = it.genreIds,
                        originalLanguage = it.originalLanguage,
                        originalTitle = it.originalTitle,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        video = it.video,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getAllUpcomingMovies(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getAllUpcomingMovies()
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!.results.map {
                    MovieResultDto(
                        adult = it.adult,
                        title = it.title,
                        id = it.id,
                        backdropPath = it.backdropPath,
                        genreIds = it.genreIds,
                        originalLanguage = it.originalLanguage,
                        originalTitle = it.originalTitle,
                        overview = it.overview,
                        popularity = it.popularity,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        video = it.video,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

//    override suspend fun getAllFamousPersons(): Flow<BaseNetworkResult<ActorsResponse>> {
//        return flow {
//            emit(BaseNetworkResult.Loading(true))
//            val response = service.getAllFamousPersons()
//            emit(BaseNetworkResult.Loading(false))
//            if (response.code() == 200) {
//                emit(BaseNetworkResult.Success(response.body()!!))
//            } else {
//                emit(BaseNetworkResult.Error("Xatolik"))
//            }
//        }
//    }
//
//    override suspend fun getAllMoviesGenres(): Flow<BaseNetworkResult<List<Genre>>> {
//        return flow {
//            emit(BaseNetworkResult.Loading(true))
//            val response = service.getAllMoviesGenres()
//            emit(BaseNetworkResult.Loading(false))
//
//            if (response.code() == 200) {
//                emit(BaseNetworkResult.Success(response.body()!!.genres))
//            } else {
//                emit(BaseNetworkResult.Error("Xatolik"))
//            }
//        }
//    }
//
//    override suspend fun getMoviesByGenre(id: Int): Flow<BaseNetworkResult<List<MovieResultDto>>> {
//        return flow {
//            emit(BaseNetworkResult.Loading(true))
//            val response = service.getMoviesByGenre(id)
//            emit(BaseNetworkResult.Loading(false))
//
//            if (response.code() == 200) {
//                emit(BaseNetworkResult.Success(response.body()!!.results.map {
//                    MovieResultDto(
//                        adult = it.adult,
//                        title = it.title,
//                        id = it.id,
//                        backdropPath = it.backdropPath,
//                        genreIds = it.genreIds,
//                        originalLanguage = it.originalLanguage,
//                        originalTitle = it.originalTitle,
//                        overview = it.overview,
//                        popularity = it.popularity,
//                        posterPath = it.posterPath,
//                        releaseDate = it.releaseDate,
//                        video = it.video,
//                        voteAverage = it.voteAverage,
//                        voteCount = it.voteCount
//                    )
//                }))
//            } else {
//                emit(BaseNetworkResult.Error("Xatolik"))
//            }
//        }
//    }
//
//    override suspend fun getDailyPopularTvShows(): Flow<BaseNetworkResult<List<MovieResultDto>>> {
//        return flow {
//            emit(BaseNetworkResult.Loading(true))
//            val response = service.getDailyPopularTvShows()
//            emit(BaseNetworkResult.Loading(false))
//            if (response.code() == 200) {
//                emit(BaseNetworkResult.Success(response.body()!!.results.map {
//                    MovieResultDto(
//                        adult = it.adult,
//                        title = it.title,
//                        id = it.id,
//                        backdropPath = it.backdropPath,
//                        genreIds = it.genreIds,
//                        originalLanguage = it.originalLanguage,
//                        originalTitle = it.originalTitle,
//                        overview = it.overview,
//                        popularity = it.popularity,
//                        posterPath = it.posterPath,
//                        releaseDate = it.releaseDate,
//                        video = it.video,
//                        voteAverage = it.voteAverage,
//                        voteCount = it.voteCount
//                    )
//                }))
//            } else {
//                emit(BaseNetworkResult.Error("Xatolik"))
//            }
//        }
//    }
}