package toniwar.projects.extremecamera.domain.use_cases.camera_use_cases

import toniwar.projects.extremecamera.domain.repositories.CameraRepository


class StartCameraUseCase(private val repository: CameraRepository) {

    fun <V, O> startCamera(view: V, owner: O){
        repository.startCamera(view, owner)
    }
}