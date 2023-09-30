package toniwar.projects.fakephotoapp.data.database

import android.util.Log
import toniwar.projects.fakephotoapp.data.DBMapper
import toniwar.projects.fakephotoapp.domain.entities.Success
import toniwar.projects.fakephotoapp.domain.entities.UploadException
import toniwar.projects.fakephotoapp.domain.entities.UploadResult
import java.lang.Exception

class DBLoader (
    private val clipArtsDAO: ClipArtsDAO,
    private val dbMapper: DBMapper
) {

     fun getResult(resultCallback: (UploadResult) -> Unit){
        Log.d("DBLoader", "getClipArtDBModelList")
         val list = clipArtsDAO.getClipArts()
                val result = try {
                    Success(dbMapper.mapToClipArtList(list))
                }
                catch (e:Exception){
                    UploadException(e)
                }
                Log.d("DBLoader", list.toString())
                resultCallback.invoke(result)
                Log.d("DBLoader", result.toString())


    }

}
