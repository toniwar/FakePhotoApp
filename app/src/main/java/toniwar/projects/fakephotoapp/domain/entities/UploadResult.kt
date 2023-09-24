package toniwar.projects.fakephotoapp.domain.entities


import java.lang.Exception

sealed interface UploadResult

class Success<T>(val clipArts: T): UploadResult

class Failure(val errorCode: Int?, val errorMessage: String?): UploadResult

class UploadException(val exception: Exception): UploadResult