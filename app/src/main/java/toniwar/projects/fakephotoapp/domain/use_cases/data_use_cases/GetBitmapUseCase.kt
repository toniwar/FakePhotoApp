package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class GetBitmapUseCase(private val dataRepository: DataRepository) {

    fun <T> getBitmap(source: T): Flow<List<Bitmap?>>{
        return dataRepository.getBitmap(source)
    }


}
