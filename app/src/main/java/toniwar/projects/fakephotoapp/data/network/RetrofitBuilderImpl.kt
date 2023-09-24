package toniwar.projects.fakephotoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilderImpl: RetrofitBuilder  {

    override val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override val service: Service by lazy {
        retrofit.create(Service::class.java)
    }



    companion object{
        private const val BASE_URL = "https://extreme-camera-server.vercel.app/"
    }
}
