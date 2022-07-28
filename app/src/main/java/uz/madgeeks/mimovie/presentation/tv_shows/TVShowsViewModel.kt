package uz.madgeeks.mimovie.presentation.tv_shows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.data.tv_shows.models.TVShowsResult
import uz.madgeeks.mimovie.domain.tv_shows.TVShowsUseCase
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    private val mainUseCase: TVShowsUseCase,
) : ViewModel() {

    private val topTVShowsList = MutableLiveData<List<TVShowsResult>?>()
    val topTVShowsListLiveData: LiveData<List<TVShowsResult>?> get() = topTVShowsList

    private val searchedTVShowsList = MutableLiveData<List<TVShowsResult>?>()
    val searchedTVShowsListLiveData: LiveData<List<TVShowsResult>?> get() = searchedTVShowsList

    private val popularTVShowsList = MutableLiveData<List<TVShowsResult>?>()
    val popularTVShowsListLiveData: LiveData<List<TVShowsResult>?> get() = popularTVShowsList

    private val onTheAirTVShowsList = MutableLiveData<List<TVShowsResult>?>()
    val onTheAirTVShowsListLiveData: LiveData<List<TVShowsResult>?> get() = onTheAirTVShowsList

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getAllTopRatedTVShows() {
        viewModelScope.launch {
            mainUseCase.getAllTopRatedTVShows().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            Log.d("DDDD", "getServicesResponse: $list")
                            topTVShowsList.value = list.results
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

    fun getPopularTVShows() {
        viewModelScope.launch {
            mainUseCase.getPopularTVShows().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            popularTVShowsList.value = list.results
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

    fun getOnTheAirTVShows() {
        viewModelScope.launch {
            mainUseCase.getOnTheAirTVShows().catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            onTheAirTVShowsList.value = list.results
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

    fun getSearchedTVShows(query : String) {
        viewModelScope.launch {
            mainUseCase.getSearchedTVShows(query).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            searchedTVShowsList.value = list.results
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