package toniwar.projects.extremecamera.data.storage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.view.View
import toniwar.projects.extremecamera.Constants
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ImageProvider @Inject constructor(private val context: Context) {

    fun <T> getBitmap(source: T): Bitmap?{
        return if(source is View){
            val bitmap = Bitmap.createBitmap(source.width,
                source.height,
                Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            val background = source.background
            if(background != null) background.draw(canvas)
            else canvas.drawColor(Color.BLACK)
            source.draw(canvas)
            bitmap
        } else null

    }


    fun saveBitmap(bitmap: Bitmap, path: String){
        val contentValues = setContentValues(path)
        val resolver = context.contentResolver
        val uri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues)
        uri?.let {
            val stream = resolver.openOutputStream(uri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream?.close()
        }


    }

    fun setContentValues(path: String): ContentValues{
        val name = SimpleDateFormat(Constants.FILENAME_FORMAT, Locale.ROOT)
            .format(System.currentTimeMillis())
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, path)
            }
        }
    }
}