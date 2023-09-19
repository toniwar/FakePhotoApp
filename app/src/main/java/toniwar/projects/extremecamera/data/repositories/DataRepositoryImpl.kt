package toniwar.projects.extremecamera.data.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toniwar.projects.extremecamera.Constants
import toniwar.projects.extremecamera.data.storage.ImageProvider
import toniwar.projects.extremecamera.data.network.ImagesLoader
import toniwar.projects.extremecamera.domain.entities.NetworkResult
import toniwar.projects.extremecamera.domain.entities.ClipArts
import toniwar.projects.extremecamera.domain.repositories.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val imagesLoader: ImagesLoader
): DataRepository {

    @Inject
    lateinit var imageProvider: ImageProvider
    override fun loadClipArts(): Flow<NetworkResult>{
        return flow{
            val result = imagesLoader.getResult()
            emit(result)
        }

    }

    override fun saveClipArtsInStorage(clipArts: ClipArts) {
        TODO("Not yet implemented")
    }

    override fun <T> saveEditedImage(img: T) : Uri?{
        return if(img is Bitmap) imageProvider.saveBitmap(img, Constants.PATH_FOR_EDITED_IMG)
        else null
    }

    override fun <T> shareImage(activity: Activity, uri: T) {
        imageProvider.shareImage(activity, uri)
    }

    override fun <T> getBitmap(source: T): Bitmap? {
        return if(source is View) imageProvider.getBitmap(source)
        else null
    }
}
