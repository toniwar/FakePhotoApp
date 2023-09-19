package toniwar.projects.extremecamera.presentation.view_models

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import toniwar.projects.extremecamera.domain.repositories.DataRepository
import toniwar.projects.extremecamera.domain.use_cases.data_use_cases.ShareImageUseCase
import toniwar.projects.extremecamera.presentation.GlideProvider
import javax.inject.Inject

class FinalResultViewModel @Inject constructor(


    private val dataRepository: DataRepository

): ViewModel() {

    @Inject
    lateinit var glideProvider: GlideProvider

    private val shareImageUseCase by lazy {
        ShareImageUseCase(dataRepository)
    }


    fun <T> setImage(view:ImageView, uri:T){
        uri?.let {
            glideProvider.setImageToView(view, uri)
        }

    }

    fun shareImage(activity: Activity, uri: Uri?){
        uri?.let {
            shareImageUseCase.shareImage(activity, uri)
        }
    }


}

