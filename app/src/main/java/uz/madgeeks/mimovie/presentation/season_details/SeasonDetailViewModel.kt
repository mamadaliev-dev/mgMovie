package uz.madgeeks.mimovie.presentation.season_details

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
import uz.madgeeks.mimovie.data.season_details.models.SeasonDetails
import uz.madgeeks.mimovie.data.tv_show_details.models.TVShowDetails
import uz.madgeeks.mimovie.domain.movie_detail.MovieDetailUseCase
import uz.madgeeks.mimovie.domain.season_details.SeasonDetailUseCase
import uz.madgeeks.mimovie.domain.tv_show_details.TVShowDetailUseCase
import javax.inject.Inject

@HiltViewModel
class SeasonDetailViewModel @Inject constructor(
    private val mainUseCase: SeasonDetailUseCase,
) : ViewModel() {
    private val seasonDetails = MutableLiveData<SeasonDetails>()
    val seasonDetailsLiveData: LiveData<SeasonDetails> get() = seasonDetails

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getTVShowDetailById(tv_id: Long, season_number : Int) {
        viewModelScope.launch {
            mainUseCase.getSeasonDetailsById(tv_id, season_number).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            seasonDetails.value = item
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