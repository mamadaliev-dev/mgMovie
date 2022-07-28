package uz.madgeeks.mimovie.data.movie_detail.model.remote.response


import com.google.gson.annotations.SerializedName

data class MovieTrailerResponse(
    @SerializedName("iso_639_1")
    var iso6391: String,
    @SerializedName("iso_3166_1")
    var iso31661: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("key")
    var key: String,
    @SerializedName("site")
    var site: String,
    @SerializedName("size")
    var size: Int,
    @SerializedName("type")
    var type: String,
    @SerializedName("official")
    var official: Boolean,
    @SerializedName("published_at")
    var publishedAt: String,
    @SerializedName("id")
    var id: String
)