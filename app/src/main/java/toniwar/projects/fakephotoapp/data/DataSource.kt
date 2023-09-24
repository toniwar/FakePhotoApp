package toniwar.projects.fakephotoapp.data

import toniwar.projects.fakephotoapp.domain.entities.UploadResult

interface DataSource {

    suspend fun getResult(): UploadResult

}
