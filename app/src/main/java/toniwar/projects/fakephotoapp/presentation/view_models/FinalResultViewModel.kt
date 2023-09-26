package toniwar.projects.fakephotoapp.presentation.view_models

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.ShareImageUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.SetImageToViewUseCase
import javax.inject.Inject

class FinalResultViewModel @Inject constructor(


    private val dataRepository: DataRepository

): ViewModel() {



    private val setImageToViewUseCase by lazy {
        SetImageToViewUseCase(dataRepository)
    }

    private val shareImageUseCase by lazy {
        ShareImageUseCase(dataRepository)
    }


    fun <T> setImage(view:ImageView, uri:T){
        uri?.let {
            setImageToViewUseCase.setImageToView(view, uri)
        }

    }

    fun shareImage(activity: Activity, uri: Uri?){
        uri?.let {
            shareImageUseCase.shareImage(activity, uri)
        }
    }


}

