package toniwar.projects.fakephotoapp.data.network

import toniwar.projects.fakephotoapp.data.DataSource
import toniwar.projects.fakephotoapp.domain.entities.Failure
import toniwar.projects.fakephotoapp.domain.entities.UploadException
import toniwar.projects.fakephotoapp.domain.entities.UploadResult
import toniwar.projects.fakephotoapp.domain.entities.Success
import java.io.IOException

class NetLoader(

    private val retrofitBuilder: RetrofitBuilder
): DataSource {


    override suspend fun getResult() : UploadResult{
        val result = retrofitBuilder.service.loadClipArts()

        val responseBody = result.body()

        return try {
            if(result.isSuccessful && responseBody != null) Success(responseBody)

            else Failure(result.code(), result.message())
        }
        catch (e: IOException){
            UploadException(e)
        }

    }

}
