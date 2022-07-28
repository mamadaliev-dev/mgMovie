package uz.madgeeks.mimovie.data.movie_detail.model.remote.response


import com.google.gson.annotations.SerializedName

data class MovieTrailerRes(
    @SerializedName("id")
    var id: Int,
    @SerializedName("results")
    var results: List<MovieTrailerResponse>?,
)