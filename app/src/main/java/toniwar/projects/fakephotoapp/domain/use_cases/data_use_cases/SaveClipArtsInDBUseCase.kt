package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import toniwar.projects.fakephotoapp.domain.entities.ClipArt
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class SaveClipArtsInDBUseCase(private val dataRepository: DataRepository) {

    fun saveClipArtsInDB(clipArts: List<ClipArt> ){
        dataRepository.saveClipArtsInDB(clipArts)
    }
}
