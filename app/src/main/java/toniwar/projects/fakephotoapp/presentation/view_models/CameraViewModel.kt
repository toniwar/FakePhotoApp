package toniwar.projects.fakephotoapp.presentation.view_models

import androidx.lifecycle.ViewModel
import toniwar.projects.fakephotoapp.domain.repositories.CameraRepository
import toniwar.projects.fakephotoapp.domain.use_cases.camera_use_cases.StartCameraUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.camera_use_cases.TakePhotoUseCase
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    repository: CameraRepository

): ViewModel() {


    private val startCameraUseCase by lazy {
        StartCameraUseCase(repository)
    }

    private val takePhotoUseCase by lazy {
        TakePhotoUseCase(repository)
    }



    fun <V, O> startCamera(view: V, owner: O){
        startCameraUseCase.startCamera(view, owner)
    }

    fun takePhoto(imgUri: (String)-> Unit)=takePhotoUseCase.takePhoto(imgUri)

}

