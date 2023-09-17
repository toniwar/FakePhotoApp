package toniwar.projects.extremecamera.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toniwar.projects.extremecamera.data.network.ImagesLoader
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.ClipArts
import toniwar.projects.extremecamera.domain.repositories.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val imagesLoader: ImagesLoader
): DataRepository {
    override fun loadClipArts(): Flow<NetworkResult>{
        return flow{
            val result = imagesLoader.getResult()
            emit(result)
        }

    }

    override fun saveClipArtsInStorage(clipArts: ClipArts) {
        TODO("Not yet implemented")
    }

    override fun <T> saveEditedImage(img: T) {
        TODO("Not yet implemented")
    }

    override fun <T> shareImage(img: T) {
        TODO("Not yet implemented")
    }
}