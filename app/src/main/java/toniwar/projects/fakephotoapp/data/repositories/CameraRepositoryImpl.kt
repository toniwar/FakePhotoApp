package toniwar.projects.fakephotoapp.data.repositories

import android.annotation.SuppressLint
import android.content.Context
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
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.data.storage.ImageProvider
import toniwar.projects.fakephotoapp.di.ActivityComponent
import toniwar.projects.fakephotoapp.domain.repositories.CameraRepository
import javax.inject.Inject

@ActivityComponent.Companion.ActivityComponentScope
class CameraRepositoryImpl @Inject constructor(
    private val context: Context
    ):
    CameraRepository {

    private lateinit var cameraController: LifecycleCameraController

    @Inject
    lateinit var imageProvider: ImageProvider


    override fun <V, O> startCamera(view: V, owner: O) {
        cameraController = LifecycleCameraController(context)
        cameraController.bindToLifecycle(owner as LifecycleOwner)
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        (view as PreviewView).controller = cameraController
    }

    override fun takePhoto(imgUri: (String) -> Unit) {
        val contentValues = imageProvider.setContentValues(Constants.PATH_FOR_TAKE_PHOTO)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()


        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(
                        Constants.TAG,
                        "Photo capture failed: ${exception.message}",
                        exception
                    )
                    Toast.makeText(context, "${exception.message}", Toast.LENGTH_SHORT).show()
                }

                @SuppressLint("RestrictedApi")
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Photo captured succeeded: ${outputFileResults.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(Constants.TAG, msg)
                    imgUri.invoke(outputFileResults.savedUri.toString())
                }
            }
        )

    }
}
