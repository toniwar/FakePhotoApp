package toniwar.projects.fakephotoapp.domain.entities


import java.lang.Exception

sealed interface NetworkResult

class Success<T>(val clipArts: T): NetworkResult

class Failure(val errorCode: Int?, val errorMessage: String?): NetworkResult

class NetworkException(val exception: Exception): NetworkResult