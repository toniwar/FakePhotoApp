package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases


import android.widget.ImageView
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class SetImageToViewUseCase(private val dataRepository: DataRepository) {

    fun <T> setImageToView(view: ImageView, source: T){
        dataRepository.setImageToView(view, source)
    }

}
