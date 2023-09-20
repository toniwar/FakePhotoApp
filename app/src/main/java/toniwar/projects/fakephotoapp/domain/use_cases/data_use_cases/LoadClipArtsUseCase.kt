package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import kotlinx.coroutines.flow.Flow
import toniwar.projects.fakephotoapp.domain.entities.NetworkResult
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class LoadClipArtsUseCase(private val repository: DataRepository) {

    fun loadClipArts(): Flow<NetworkResult> {
        return repository.loadClipArts()
    }

}