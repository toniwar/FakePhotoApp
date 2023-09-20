package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.graphics.Bitmap
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class GetBitmapUseCase(private val dataRepository: DataRepository) {

    fun <T> getBitmap(source: T): Bitmap?{
        return dataRepository.getBitmap(source)
    }


}
