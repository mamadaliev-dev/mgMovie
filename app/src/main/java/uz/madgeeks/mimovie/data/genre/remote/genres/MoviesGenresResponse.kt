package uz.madgeeks.mimovie.data.genre.remote.genres


import com.google.gson.annotations.SerializedName
import uz.madgeeks.mimovie.data.genre.local.Genre

data class MoviesGenresResponse(
    @SerializedName("genres")
    var genres: List<Genre>
)