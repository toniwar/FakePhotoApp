package toniwar.projects.fakephotoapp.data.storage

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.R
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


    fun saveBitmap(bitmap: Bitmap, path: String): Uri? {
        val contentValues = setContentValues(path)
        val resolver = context.contentResolver
        val uri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues)
        uri?.let {
            val stream = resolver.openOutputStream(uri)
            if(stream != null) bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream?.close()
        }

        return uri

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
}
