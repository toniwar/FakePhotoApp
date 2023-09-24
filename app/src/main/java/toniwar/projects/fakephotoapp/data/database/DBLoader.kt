package toniwar.projects.fakephotoapp.data.database

import toniwar.projects.fakephotoapp.data.DBMapper
import toniwar.projects.fakephotoapp.data.DataSource
import toniwar.projects.fakephotoapp.domain.entities.Failure
import toniwar.projects.fakephotoapp.domain.entities.Success
import toniwar.projects.fakephotoapp.domain.entities.UploadException
import toniwar.projects.fakephotoapp.domain.entities.UploadResult
import java.lang.Exception

class DBLoader (
    private val clipArtsDAO: ClipArtsDAO,
    private val dbMapper: DBMapper
): DataSource {


    override suspend fun getResult(): UploadResult{

        val result = clipArtsDAO.getClipArts()

        return try {

            if(result != null) {
                Success(dbMapper.mapToClipArtList(result))
            }
            else Failure(null, "There is nothing in the DB")
        }

        catch (e: Exception){
            UploadException(e)
        }
    }

}
