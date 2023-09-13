package toniwar.projects.extremecamera.domain.repositories


interface CameraRepository {

    fun <V, O> startCamera(view: V, owner: O )
    fun takePhoto(imgUri: (String)-> Unit)

}
