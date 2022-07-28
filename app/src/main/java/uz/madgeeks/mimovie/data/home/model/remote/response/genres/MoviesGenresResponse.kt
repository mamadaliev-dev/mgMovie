package uz.madgeeks.mimovie.data.home.model.remote.response.genres


import com.google.gson.annotations.SerializedName

data class MoviesGenresResponse(
    @SerializedName("genres")
    var genres: List<Genre>
)