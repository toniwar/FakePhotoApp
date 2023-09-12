package toniwar.projects.extremecamera.domain.entities

import java.lang.Error
import java.lang.Exception

sealed interface NetworkResult

class Success(val samples: Samples): NetworkResult

class Failure(val error: Error): NetworkResult

class NetworkException(val exception: Exception): NetworkResult