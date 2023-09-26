package toniwar.projects.fakephotoapp.data.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.data.DBMapper
import toniwar.projects.fakephotoapp.data.database.ClipArtsDAO
import toniwar.projects.fakephotoapp.data.database.DBLoader
import toniwar.projects.fakephotoapp.data.storage.ImageProvider
import toniwar.projects.fakephotoapp.data.network.NetLoader
import toniwar.projects.fakephotoapp.data.network.RetrofitBuilder
import toniwar.projects.fakephotoapp.data.storage.ClipArtsSharedPrefs
import toniwar.projects.fakephotoapp.data.storage.GlideProvider
import toniwar.projects.fakephotoapp.domain.entities.ClipArt
import toniwar.projects.fakephotoapp.domain.entities.UploadResult
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository
import java.lang.Exception
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val retrofit: RetrofitBuilder,
    private val clipArtsDAO: ClipArtsDAO
): DataRepository {


    @Inject
    lateinit var imageProvider: ImageProvider

    @Inject
    lateinit var glideProvider: GlideProvider

    @Inject
    lateinit var sharedPrefs: ClipArtsSharedPrefs

    private val dbMapper = DBMapper()


    private val dbLoader by lazy {
        DBLoader(clipArtsDAO, dbMapper)
    }

    private val netLoader by lazy {
        NetLoader(retrofit)
    }



    override fun loadClipArts(isLocal: Boolean): Flow<UploadResult>{

        val source = if(!isLocal) netLoader else dbLoader
        return flow{
            val result = source.getResult()
            emit(result)
        }

    }

    override fun saveClipArtsInDB(clipArts: List<ClipArt>) {

        val list = dbMapper.mapToClipArtDBModelList(clipArts)

        list.forEach {
            try {
                clipArtsDAO.addClipArt(it)
            }
            catch (e: Exception){
                Log.e("DB", "${e.message}")
            }

        }
    }

    override fun <T> saveImage(
        img: T,
        path: String,
        id: Int?,
        format: CompressFormat,
        mimeType: String
    ): Uri? {
        return if(img is Bitmap) imageProvider.saveBitmap(
            img,
            Constants.PATH_FOR_EDITED_IMG,
            id,
            format,
            mimeType
        )
        else null
    }

    override fun <T> shareImage(activity: Activity, uri: T) {
        imageProvider.shareImage(activity, uri)
    }

    override fun <T> getBitmap(source: T): Bitmap? {
        return if(source is View) imageProvider.getBitmap(source)
        else null
    }



    override fun <T> setImageToView(view: ImageView, source: T) {
        glideProvider.setImageToView(view, source)
    }

    override fun <T> inlineImageToView(view: ViewGroup, source: T): View {
        return glideProvider.inlineClipArtView(view, source)
    }



    override fun <T> writeToSharedPrefs(name: Constants.PrefDataType, data: T) {

        try {
            sharedPrefs.write(name.name, data)
        }
        catch (e: Exception){
            Log.e("WriteToSP", e.message.toString())
        }



    }

    override fun <T> readFromSharedPrefs(name: Constants.PrefDataType): T? {

        return when(name){
            Constants.PrefDataType.LAST_ID -> sharedPrefs.readInt(name.name) as T
            Constants.PrefDataType.SIZE -> sharedPrefs.readInt(name.name) as T
            Constants.PrefDataType.IS_RECORDED_IN_DB -> sharedPrefs.readBoolean(name.name) as T
            else -> return null
        }
    }
}
