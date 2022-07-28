package uz.madgeeks.mimovie.presentation.movie_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieDetailResponse
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Review
import uz.madgeeks.mimovie.domain.movie_detail.MovieDetailUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val mainUseCase: MovieDetailUseCase,
) : ViewModel() {

    private val movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetailLiveData: LiveData<MovieDetailResponse> get() = movieDetail

    private val reviews = MutableLiveData<List<Review>>()
    val reviewsLiveData: LiveData<List<Review>> get() = reviews

    private val movieTrailer = MutableLiveData<MovieTrailerRes>()
    val movieTrailerLiveData: LiveData<MovieTrailerRes> get() = movieTrailer

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val similarMoviesList = MutableLiveData<List<MovieResultDto>>()
    val similarMoviesListLiveData: LiveData<List<MovieResultDto>> get() = similarMoviesList

    private val credits = MutableLiveData<Credits>()
    val creditsLiveData: LiveData<Credits> get() = credits


    fun getMovieDetailById(id: Long) {
        viewModelScope.launch {
            mainUseCase.getMovieDetailById(id).catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            movieDetail.value = item
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        //loading
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

    fun getMovieTrailerListById(id: Long) {
        viewModelScope.launch {
            mainUseCase.getMovieTrailerListById(id).catch {
                Log.d("DDDD", "getMovieTrailerListById: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            movieTrailer.value = item
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        //loading
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

//    fun getReviews(id: Long) {
//        viewModelScope.launch {
//            mainUseCase.getReviews(id).catch {
//                //xatolik beriladi
//                Log.d("DDDD", "getServicesResponse: $this")
//            }.collect { result ->
//                when (result) {
//                    is BaseNetworkResult.Success -> {
//                        result.data?.let { item ->
//                            reviews.value = item
//                        }
//                    }
//                    is BaseNetworkResult.Error -> {
//                        result.message.let {
//                            _errorLiveData.value = "ERROR"
//                        }
//                    }
//                    is BaseNetworkResult.Loading -> {
//                        //loading
//                        result.isLoading?.let {
//                            _isLoadingLiveData.value = it
//                        }
//                    }
//                }
//            }
//        }
//    }


    fun getCredits(id: Long) {
        viewModelScope.launch {
            mainUseCase.getCredits(id).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            credits.value = item
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = "ERROR"
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        //loading
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

//    fun getSimilarMovies(id : Long) {
//        mainUseCase.getSimilarMovies(id).observeForever {
//            when (it) {
//                is BaseNetworkResult.Loading -> {
//
//                }
//                is BaseNetworkResult.Success -> {
//                    it.data!!.results.forEach { ss->
//                        Log.d("DDDD", "Similars: ${ss.originalTitle}")
//                    }
//                    similarMoviesList.value = it.data.results.map { similar ->
//                        MovieResultDto(
//                            adult = similar.adult,
//                            title = similar.title,
//                            id = similar.id,
//                            backdropPath = similar.backdropPath,
//                            genreIds = similar.genreIds,
//                            originalLanguage = similar.originalLanguage,
//                            originalTitle = similar.originalTitle,
//                            overview = similar.overview,
//                            popularity = similar.popularity,
//                            posterPath = similar.posterPath,
//                            releaseDate = similar.releaseDate,
//                            video = similar.video,
//                            voteAverage = similar.voteAverage,
//                            voteCount = similar.voteCount
//                        )
//                    }
//                    Log.d("DDDD", "getAllNewMoviesRx: ${it.data}")
//                }
//                is BaseNetworkResult.Error -> {
//
//                }
//            }
//        }
//    }
}