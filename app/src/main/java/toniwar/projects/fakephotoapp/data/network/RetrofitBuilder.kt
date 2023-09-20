package toniwar.projects.fakephotoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitBuilder @Inject constructor() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val samplesService: Service by lazy {
        retrofit.create(Service::class.java)
    }



    companion object{
        private const val BASE_URL = "https://extreme-camera-server.vercel.app/"
    }
}
