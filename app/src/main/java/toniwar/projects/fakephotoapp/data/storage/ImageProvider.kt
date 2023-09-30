package toniwar.projects.fakephotoapp.data.storage

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ImageProvider @Inject constructor(private val context: Context) {

    fun <T> getBitmap(source: T): Flow<List<Bitmap?>> {
        val bitmapList = mutableListOf<Bitmap?>()

        when(source){
           is View ->{
                   bitmapList.add(bitmapFromView(source))
           }

            is List<*> -> {
                source.forEach {
                    try {
                        bitmapList.add(bitmapFromCollection(it))
                    }
                    catch (e: Exception){
                        Log.e(TAG, e.message.toString())
                    }

                }
            }
        }

        return flow {
            emit(bitmapList.toList())
        }
    }

    private fun <T> bitmapFromCollection(itemType: T): Bitmap?{
        return when(itemType){
            is Uri -> bitmapFromUri(itemType)
            is String -> bitmapFromUrl(itemType)
            else -> null
        }
    }

    private fun bitmapFromView(view: View?): Bitmap?{
        if (view == null) return null
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val background = view.background
        if(background != null) background.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }



    private fun bitmapFromUrl(path: String?): Bitmap?{
        if(path.isNullOrBlank()) return null
        return Glide.with(context)
            .asBitmap()
            .load(path)
            .submit()
            .get()
    }

    private fun bitmapFromUri(uri: Uri?): Bitmap?{
        if(uri == null) return null
        val stream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(stream)
        stream?.close()
        return bitmap
    }


    fun saveBitmap(
        bitmap: Bitmap?,
        path: String,
        id: Int?,
        format: CompressFormat,
        mimeType: String
    ): Uri? {
        val (contentValues, name) = setContentValues(path, mimeType, id)
        val resolver = context.contentResolver
        val uri = checkUri(contentValues, name, mimeType)
        uri?.let {
            val stream = resolver.openOutputStream(it)


            if(stream != null) {
                bitmap?.compress(format, 100, stream)
                stream.close()
            }

        }

        return uri
    }

    private fun checkUri(
        contentValues: ContentValues,
        name: String,
        mimeType: String
    ):Uri?{
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.DATA),
            MediaStore.MediaColumns.DISPLAY_NAME + " = ? AND " + MediaStore.MediaColumns.MIME_TYPE + " = ?",
            arrayOf(name, mimeType),
            null
        )

        var fileUri: Uri? = null
        if(cursor != null && cursor.count > 0){
            while (cursor.moveToNext()){
                val nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                if(nameIndex > -1){
                    val displayName = cursor.getString(nameIndex)
                    if(displayName == name){
                        val idIndex = cursor.getColumnIndex(MediaStore.MediaColumns._ID)
                        if(idIndex > -1){
                            val id = cursor.getLong(idIndex)
                            fileUri = ContentUris.withAppendedId(uri, id)
                        }
                    }
                }
            }
            cursor.close()
        }
        else{
            fileUri = context.contentResolver.insert(uri, contentValues)
        }
        return fileUri
    }

    fun setContentValues(path: String, mimeType: String,id: Int? = null):
            Pair<ContentValues, String>{
        val name = if(id == null)SimpleDateFormat(Constants.FILENAME_FORMAT, Locale.ROOT)
            .format(System.currentTimeMillis())
                else "img-$id"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, path)
            }
        }

        return Pair(contentValues, name)
    }

    fun<T> shareImage(activity: Activity, uri: T?){
        if(uri is Uri?){
            val msg = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                uri?.let {
                    putExtra(Intent.EXTRA_STREAM, uri )
                }
            }

            try{

                activity.startActivity(Intent.createChooser(msg,""))
            }
            catch (e: ActivityNotFoundException){
                Toast.makeText(context, R.string.txt_for_activity_not_found_exc,
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object{
        const val TAG = "Image provider"
    }

}
