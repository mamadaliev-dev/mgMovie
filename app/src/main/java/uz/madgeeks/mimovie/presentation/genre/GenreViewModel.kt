package uz.madgeeks.mimovie.presentation.genre

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.genre.local.Genre
import uz.madgeeks.mimovie.data.home.model.local.MovieResultDto
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResult
import uz.madgeeks.mimovie.domain.genre.GenreUseCase
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val mainUseCase: GenreUseCase,
) : ViewModel() {

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val genreList = MutableLiveData<List<Genre>>()
    val genreListLiveData: LiveData<List<Genre>> get() = genreList

    private val genreTVList = MutableLiveData<List<Genre>>()
    val genreTVListLiveData: LiveData<List<Genre>> get() = genreTVList

    private val moviesByGenresList = MutableLiveData<List<MovieResultDto>>()
    val moviesByGenresListLiveData: LiveData<List<MovieResultDto>> get() = moviesByGenresList

    private val tvShowsByGenreList = MutableLiveData<List<TVShowsResult>?>()
    val tvShowsByGenreListLiveData: LiveData<List<TVShowsResult>?> get() = tvShowsByGenreList

    fun getAllMoviesGenres() {
        viewModelScope.launch {
            mainUseCase.getAllMoviesGenres().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            genreList.value = list.genres

                        }
                    }
                    is BaseNetworkResult.Error -> {
                        Log.d("DDDD", "XATOLIK")
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

    fun getAllTVShowGenres() {
        viewModelScope.launch {
            mainUseCase.getAllTVShowGenres().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            genreTVList.value = list.genres
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        Log.d("DDDD", "XATOLIK")
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

    fun getMoviesByGenre(id: Int) {
        viewModelScope.launch {
            mainUseCase.getMoviesByGenre(id).catch {
                Log.d("DDDD", "getServicesResponse: Genre")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            moviesByGenresList.value = list
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        Log.d("DDDD", "XATOLIK")
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

    fun getTVShowsByGenre(id : Int) {
        viewModelScope.launch {
            mainUseCase.getTVShowsByGenre(id).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            tvShowsByGenreList.value = list.results
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
}