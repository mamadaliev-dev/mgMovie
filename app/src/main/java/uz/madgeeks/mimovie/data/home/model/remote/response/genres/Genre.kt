package uz.madgeeks.mimovie.data.home.model.remote.response.genres

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)