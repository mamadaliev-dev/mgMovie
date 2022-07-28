package uz.madgeeks.mimovie.data.movie_detail.model.remote.response

import com.google.gson.annotations.SerializedName

data class Credits(
  @SerializedName("id")
  val id: Int,
  @SerializedName("cast")
  val cast : List<Cast> ?=null,
  @SerializedName("crew")
  val crew: List<Crew> ?=null
)