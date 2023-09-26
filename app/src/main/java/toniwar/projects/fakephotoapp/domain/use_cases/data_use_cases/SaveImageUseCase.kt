package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class SaveImageUseCase(private val repository: DataRepository)
{
    fun <T> saveImage(img: T,
                      path: String,
                      id: Int?,
                      format: CompressFormat,
                      mimeType: String): Uri?{
        return repository.saveImage(img, path, id, format, mimeType)
    }
}
