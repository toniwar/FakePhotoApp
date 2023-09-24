package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import kotlinx.coroutines.flow.Flow
import toniwar.projects.fakephotoapp.domain.entities.UploadResult
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class LoadClipArtsUseCase(private val repository: DataRepository) {

    fun loadClipArts(isLocal: Boolean): Flow<UploadResult> {
        return repository.loadClipArts(isLocal)
    }

}
