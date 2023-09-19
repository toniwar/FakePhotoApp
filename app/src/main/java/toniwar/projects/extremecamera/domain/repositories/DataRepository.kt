package toniwar.projects.extremecamera.domain.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.ClipArts


interface DataRepository {

    fun loadClipArts(): Flow<NetworkResult>

    fun saveClipArtsInStorage(clipArts: ClipArts)

    fun <T> saveEditedImage(img: T): Uri?

    fun <T> shareImage(activity: Activity, uri: T)

    fun <T> getBitmap(source: T): Bitmap?
}

