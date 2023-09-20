package toniwar.projects.fakephotoapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import toniwar.projects.fakephotoapp.domain.entities.ClipArts

interface Service {

    @GET("destructive_element")
    suspend fun loadClipArts(): Response<ClipArts>
}
