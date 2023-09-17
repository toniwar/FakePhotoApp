package toniwar.projects.extremecamera.data.network

import retrofit2.Response
import retrofit2.http.GET
import toniwar.projects.extremecamera.domain.entities.ClipArts

interface Service {

    @GET("destructive_element")
    suspend fun loadClipArts(): Response<ClipArts>
}
