package toniwar.projects.fakephotoapp.data.network

import toniwar.projects.fakephotoapp.domain.entities.Failure
import toniwar.projects.fakephotoapp.domain.entities.NetworkException
import toniwar.projects.fakephotoapp.domain.entities.NetworkResult
import toniwar.projects.fakephotoapp.domain.entities.Success
import java.io.IOException
import javax.inject.Inject

class ImagesLoader @Inject constructor(

    private val retrofitBuilder: RetrofitBuilder
) {


    suspend fun getResult() : NetworkResult{
        val result = retrofitBuilder.samplesService.loadClipArts()

        val responseBody = result.body()

        return try {
            if(result.isSuccessful && responseBody != null) Success(responseBody)

            else Failure(result.code(), result.message())
        }
        catch (e: IOException){
            NetworkException(e)
        }

    }

}
