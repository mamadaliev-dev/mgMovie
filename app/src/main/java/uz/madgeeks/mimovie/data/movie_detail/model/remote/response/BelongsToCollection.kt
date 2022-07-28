package uz.madgeeks.mimovie.data.movie_detail.model.remote.response


import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("poster_path")
    var posterPath: Any,
    @SerializedName("backdrop_path")
    var backdropPath: Any
)