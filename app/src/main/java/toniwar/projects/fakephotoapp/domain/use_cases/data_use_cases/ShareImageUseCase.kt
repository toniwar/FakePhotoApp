package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import android.app.Activity
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class ShareImageUseCase(private val dataRepository: DataRepository) {

    fun <T> shareImage(activity: Activity, uri: T){
        dataRepository.shareImage(activity, uri)
    }


}
