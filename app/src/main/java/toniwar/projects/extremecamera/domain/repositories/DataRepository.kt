package toniwar.projects.extremecamera.domain.repositories

import kotlinx.coroutines.flow.Flow
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.Samples


interface DataRepository {

    fun loadSamples(): Flow<NetworkResult>

    fun saveSamplesInStorage(samples: Samples)

    fun <T> saveEditedImage(img: T)

    fun <T> shareImage(img: T)
}
