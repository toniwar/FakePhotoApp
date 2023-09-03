package toniwar.projects.extreamcamera.presentation

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.Locale

class CameraController(
    private val context: Context,
    private val cameraView: PreviewView,
    private val owner: LifecycleOwner,
    ) {

    private lateinit var cameraController: LifecycleCameraController


    fun startCamera(){
        cameraController = LifecycleCameraController(context)
        cameraController.bindToLifecycle(owner)
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraView.controller = cameraController
    }

    fun takePhoto(){
        val name = SimpleDateFormat(MainActivity.FILENAME_FORMAT, Locale.ROOT)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ExtremeCamera-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback{
                override fun onError(exception: ImageCaptureException) {
                    Log.e(MainActivity.TAG,
                        "Photo capture failed: ${exception.message}",
                        exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Photo captured succeeded: ${outputFileResults.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(MainActivity.TAG, msg)
                }
            }
        )
    }
}