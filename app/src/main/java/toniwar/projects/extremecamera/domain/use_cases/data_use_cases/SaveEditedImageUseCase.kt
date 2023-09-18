package toniwar.projects.extremecamera.domain.use_cases.data_use_cases

import toniwar.projects.extremecamera.domain.repositories.DataRepository

class SaveEditedImageUseCase(private val repository: DataRepository)
{
    fun <T> saveEditedImage(img: T){
        repository.saveEditedImage(img)
    }
}
