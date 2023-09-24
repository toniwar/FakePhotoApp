package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class SaveClipArtImageInStorageUseCase( private val dataRepository: DataRepository) {

    fun saveClipArtImageInStorage(path: String?) = dataRepository.saveClipArtImageInStorage(path)

}
