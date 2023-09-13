package toniwar.projects.extremecamera.data.network

import toniwar.projects.extremecamera.domain.entities.Failure
import toniwar.projects.extremecamera.domain.entities.NetworkException
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.Success
import java.io.IOException
import javax.inject.Inject

class ImagesLoader @Inject constructor(

    private val retrofitBuilder: RetrofitBuilder
) {


    suspend fun getResult() : NetworkResult{
        val result = retrofitBuilder.samplesService.loadSamples()

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
