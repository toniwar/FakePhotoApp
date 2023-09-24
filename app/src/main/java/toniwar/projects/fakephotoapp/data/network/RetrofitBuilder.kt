package toniwar.projects.fakephotoapp.data.network

import retrofit2.Retrofit

interface RetrofitBuilder {

    val retrofit: Retrofit

    val service: Service
}

