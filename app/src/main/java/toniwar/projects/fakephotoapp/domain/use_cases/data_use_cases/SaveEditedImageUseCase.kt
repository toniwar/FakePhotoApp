package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.net.Uri
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class SaveEditedImageUseCase(private val repository: DataRepository)
{
    fun <T> saveEditedImage(img: T): Uri?{
        return repository.saveEditedImage(img)
    }
}
