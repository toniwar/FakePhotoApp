package toniwar.projects.extremecamera.domain.repositories

import kotlinx.coroutines.flow.Flow
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.ClipArts


interface DataRepository {

    fun loadClipArts(): Flow<NetworkResult>

    fun saveClipArtsInStorage(clipArts: ClipArts)

    fun <T> saveEditedImage(img: T)

    fun <T> shareImage(img: T)
}
