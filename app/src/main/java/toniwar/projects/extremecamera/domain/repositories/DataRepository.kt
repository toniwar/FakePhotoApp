package toniwar.projects.extremecamera.domain.repositories

interface DataRepository {

    fun loadSamples()

    fun saveSamplesInStorage()

    fun saveEditedImage()
}