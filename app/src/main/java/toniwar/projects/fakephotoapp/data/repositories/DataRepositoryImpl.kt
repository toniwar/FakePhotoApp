package toniwar.projects.fakephotoapp.data.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.data.storage.ImageProvider
import toniwar.projects.fakephotoapp.data.network.ImagesLoader
import toniwar.projects.fakephotoapp.domain.entities.NetworkResult
import toniwar.projects.fakephotoapp.domain.entities.ClipArts
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository
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
