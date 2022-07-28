package uz.madgeeks.mimovie.presentation.home

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
import uz.madgeeks.mimovie.domain.home.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUseCase: HomeUseCase,
) : ViewModel() {

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val movieList = MutableLiveData<List<MovieResultDto>>()
    val moviesListLiveData: LiveData<List<MovieResultDto>> get() = movieList

    private val popularMovieList = MutableLiveData<List<MovieResultDto>>()
    val popularMovieListLiveData: LiveData<List<MovieResultDto>> get() = popularMovieList

    private val topRatedMovieList = MutableLiveData<List<MovieResultDto>>()
    val topRatedMovieListLiveData: LiveData<List<MovieResultDto>> get() = topRatedMovieList

    private val upcomingMovieList = MutableLiveData<List<MovieResultDto>>()
    val upcomingMovieListLiveData: LiveData<List<MovieResultDto>> get() = upcomingMovieList

    fun getAllNewMovies() {
        viewModelScope.launch {
            mainUseCase.getAllNewMovies().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            Log.d("DDDD", "getServicesResponse: $list")
                            movieList.value = list
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

    fun getAllPopularMovies() {
        viewModelScope.launch {
            mainUseCase.getAllPopularMovies().catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            Log.d("DDDD", "getServicesResponse: $list")
                            popularMovieList.value = list
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        //network error
                        Log.d("DDDD", "getServicesResponse: xatooooooo")
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

    fun getAllTopRatedMovies() {
        viewModelScope.launch {
            mainUseCase.getAllTopRatedMovies().catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            Log.d("DDDD", "getServicesResponse: $list")
                            topRatedMovieList.value = list
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        //network error
                        Log.d("DDDD", "getServicesResponse: xatooooooo")
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

    fun getAllUpcomingMovies() {
        viewModelScope.launch {
            mainUseCase.getAllUpcomingMovies().catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            Log.d("DDDD", "getServicesResponse: $list")
                            upcomingMovieList.value = list
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        //network error
                        Log.d("DDDD", "getServicesResponse: xatooooooo")
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

//    fun getAllFamousPersons() {
//        viewModelScope.launch {
//            mainUseCase.getAllFamousPersons().catch {
//                //xatolik beriladi
//                Log.d("DDDD", "getServicesResponse: $this")
//            }.collect { result ->
//                when (result) {
//                    is BaseNetworkResult.Success -> {
//                        result.data?.let { list ->
//                            actorsList.value = list.results.map {
//                                ActorsResultDto(
//                                    adult = it.adult,
//                                    name = it.name,
//                                    gender = it.gender,
//                                    knownForDepartment = it.knownForDepartment,
//                                    profilePath = it.profilePath,
//                                    id = it.id,
//                                    popularity = it.popularity,
//                                    mediaType = it.mediaType,
//                                    place_of_birth = it.place_of_birth,
//                                    birthday = it.birthday,
//                                )
//                            }
//                            list.results.forEach { item ->
//                                Log.d("DDDD", item.name)
//                            }
//
//                        }
//                    }
//                    is BaseNetworkResult.Error -> {
//                        Log.d("DDDD", "XATOLIK")
//                    }
//                    is BaseNetworkResult.Loading -> {
//                        result.isLoading?.let {
//                            _isLoadingLiveData.value = it
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun getAllMoviesGenres() {
//        viewModelScope.launch {
//            mainUseCase.getAllMoviesGenres().catch {
//                //xatolik beriladi
//                Log.d("DDDD", "getServicesResponse: $this")
//            }.collect { result ->
//                when (result) {
//                    is BaseNetworkResult.Success -> {
//                        result.data?.let { list ->
//                            genreList.value = list
//                            list.forEach { item ->
//                                Log.d("DDDD", item.name)
//                            }
//
//                        }
//                    }
//                    is BaseNetworkResult.Error -> {
//                        Log.d("DDDD", "XATOLIK")
//                    }
//                    is BaseNetworkResult.Loading -> {
//                        result.isLoading?.let {
//                            _isLoadingLiveData.value = it
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun getMoviesByGenre(id: Int) {
//        viewModelScope.launch {
//            mainUseCase.getMoviesByGenre(id).catch {
//                //xatolik beriladi
//                Log.d("DDDD", "getServicesResponse: $this")
//            }.collect { result ->
//                when (result) {
//                    is BaseNetworkResult.Success -> {
//                        result.data?.let { list ->
//                            moviesByGenresList.value = list
//                        }
//                    }
//                    is BaseNetworkResult.Error -> {
//                        Log.d("DDDD", "XATOLIK")
//                    }
//                    is BaseNetworkResult.Loading -> {
//                        result.isLoading?.let {
//                            _isLoadingLiveData.value = it
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun getDailyPopularTvShows() {
//        viewModelScope.launch {
//            mainUseCase.getDailyPopularTvShows().catch {
//                Log.d("DDDD", "getDailyPopularTvShows: POPULARR")
//                Log.d("DDDD", "getServicesResponse: $this")
//            }.collect { result ->
//                when (result) {
//                    is BaseNetworkResult.Success -> {
//                        result.data?.let { list ->
//                            Log.d("DDDD", "getServicesResponse: $list")
//                            tvShowsList.value = list
//                        }
//                    }
//                    is BaseNetworkResult.Error -> {
//                        //network error
//                        Log.d("DDDD", "getServicesResponse: xatooooooo")
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
}