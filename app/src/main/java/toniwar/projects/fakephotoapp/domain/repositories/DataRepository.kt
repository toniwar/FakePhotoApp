package toniwar.projects.fakephotoapp.domain.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.coroutines.flow.Flow
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.domain.entities.ClipArt
import toniwar.projects.fakephotoapp.domain.entities.UploadResult



interface DataRepository {

    fun loadClipArts(isLocal: Boolean): Flow<UploadResult>

    fun saveClipArtsInDB(clipArts: List<ClipArt>)

    fun <T> saveEditedImage(img: T): Uri?

    fun <T> shareImage(activity: Activity, uri: T)

    fun <T> getBitmap(source: T): Bitmap?


    fun <T> setImageToView(view: ImageView, source: T)

    fun <T> inlineImageToView(view: ViewGroup, source: T ): View

    fun saveClipArtImageInStorage(path: String?): Uri?

    fun <T> writeToSharedPrefs(name: Constants.PrefDataType, data: T)

    fun <T> readFromSharedPrefs(name: Constants.PrefDataType): T?



}

