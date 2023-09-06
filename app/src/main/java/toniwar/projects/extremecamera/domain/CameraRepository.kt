package toniwar.projects.extremecamera.domain


interface CameraRepository {

    fun <V, O> startCamera(view: V, owner: O )
    fun takePhoto(imageUrl:(String)->Unit)

}
