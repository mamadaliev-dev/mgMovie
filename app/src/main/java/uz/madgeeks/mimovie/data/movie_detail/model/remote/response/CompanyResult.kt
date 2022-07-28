package uz.madgeeks.mimovie.data.movie_detail.model.remote.response

import com.google.gson.annotations.SerializedName

data class CompanyResult(
    @SerializedName("description")
    var description: String,
    @SerializedName("headquarters")
    var headquarters: String,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("logo_path")
    var logo_path: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("origin_country")
    var origin_country: String,
)