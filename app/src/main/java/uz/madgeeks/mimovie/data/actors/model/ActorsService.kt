package uz.madgeeks.mimovie.data.actors.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.data.actors.model.local.ActorsResultDto

interface ActorsService {
    @GET("person/{person_id}")
    suspend fun getPersonDetailById(
        @Path("person_id") person_id: Long,
        @Query("api_key") closeReason: String = BuildConfig.TOKEN,
    ): Response<ActorsResultDto>
}