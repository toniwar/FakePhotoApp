package toniwar.projects.fakephotoapp.domain.use_cases.camera_use_cases

import toniwar.projects.fakephotoapp.domain.repositories.CameraRepository

class TakePhotoUseCase(private val repository: CameraRepository){

    fun takePhoto(imgUri: (String)-> Unit){
        repository.takePhoto{imgUri.invoke(it)}
    }
}