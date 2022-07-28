package uz.madgeeks.mimovie.presentation.tv_show_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Credits
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.MovieTrailerRes
import uz.madgeeks.mimovie.data.tv_show_details.models.TVShowDetails
import uz.madgeeks.mimovie.domain.tv_show_details.TVShowDetailUseCase
import javax.inject.Inject

@HiltViewModel
class TVShowDetailViewModel @Inject constructor(
    private val mainUseCase: TVShowDetailUseCase,
) : ViewModel() {
    private val tvShowDetails = MutableLiveData<TVShowDetails>()
    val tvShowDetailsLiveData: LiveData<TVShowDetails> get() = tvShowDetails

    private val movieTrailer = MutableLiveData<MovieTrailerRes>()
    val movieTrailerLiveData: LiveData<MovieTrailerRes> get() = movieTrailer

    private val credits = MutableLiveData<Credits>()
    val creditsLiveData: LiveData<Credits> get() = credits

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getTVShowDetailById(id: Long) {
        viewModelScope.launch {
            mainUseCase.getTVShowDetailById(id).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            tvShowDetails.value = item
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

    fun getTVTrailerListById(id: Long) {
        viewModelScope.launch {
            mainUseCase.getTVTrailerListById(id).catch {
                //xatolik beriladi
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

}