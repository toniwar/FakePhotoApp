package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.view.ViewGroup
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class InlineImageToViewUseCase(private val dataRepository: DataRepository) {

    fun <T> inlineImageToView(view: ViewGroup, source: T)
    = dataRepository.inlineImageToView(view, source)

}
