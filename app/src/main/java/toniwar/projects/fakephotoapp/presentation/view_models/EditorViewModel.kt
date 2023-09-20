package toniwar.projects.fakephotoapp.presentation.view_models

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import toniwar.projects.fakephotoapp.domain.entities.Failure
import toniwar.projects.fakephotoapp.domain.entities.NetworkException
import toniwar.projects.fakephotoapp.domain.entities.ClipArts
import toniwar.projects.fakephotoapp.domain.entities.Success
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.GetBitmapUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.LoadClipArtsUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.SaveEditedImageUseCase
import toniwar.projects.fakephotoapp.presentation.ClipArtView
import toniwar.projects.fakephotoapp.presentation.ClipArtViewController
import toniwar.projects.fakephotoapp.presentation.EditorMenu
import toniwar.projects.fakephotoapp.presentation.GlideProvider
import javax.inject.Inject

class EditorViewModel @Inject constructor(
    private val context: Context,
    private val dataRepository: DataRepository,
): ViewModel() {


    private val loadClipArtsUseCase by lazy {
        LoadClipArtsUseCase(dataRepository)
    }

    private val saveEditedImageUseCase by lazy {
        SaveEditedImageUseCase(dataRepository)
    }

    private val getBitmapUseCase by lazy {
        GetBitmapUseCase(dataRepository)
    }

    @Inject
    lateinit var glideProvider: GlideProvider

    private val mutableClipArtsList = MutableStateFlow<ClipArts?>(null)

    val clipArtsList:StateFlow<ClipArts?> get() = mutableClipArtsList.asStateFlow()


    @SuppressLint("StaticFieldLeak")
    private var clipArtView: ClipArtView? = null

    fun showMenu(guideline: List<Guideline>, menuType: EditorMenu.MenuTypes ){
        EditorMenu.menu(guideline, menuType)

    }



    fun inlineImage(view: ViewGroup, uri: String, controller: ClipArtViewController){

        clipArtView = glideProvider.inlineClipArtView(view, uri)

        clipArtView?.let {
            controller.setClipArtView(it)
        }
    }


    fun removeClipArtView(view: ViewGroup){
        clipArtView?.let {
            clipArtView = null
            view.removeView(it)
            view.invalidate()
        }

    }


    fun <T> setImage(view: ImageView, uri: T){
        glideProvider.setImageToView(view, uri)
    }



    fun saveImage(view: View, uri: (Uri?)-> Unit){
        val bitmap = getBitmapUseCase.getBitmap(view)
        bitmap?.let {
            uri.invoke(saveEditedImageUseCase.saveEditedImage(bitmap))
        }

    }


    private fun loadClipArts(){

        loadClipArtsUseCase.loadClipArts().onEach { result->
            when(result){
                is Success<*> -> {
                    if(result.clipArts is ClipArts)
                        mutableClipArtsList.value = result.clipArts
                }

                is Failure -> {
                    Log.e(
                        "NetworkError", "${result.errorCode}: ${result.errorMessage}"
                    )
                    Toast.makeText(context,
                        "${result.errorCode}: ${result.errorMessage}",
                        Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkException -> {
                    Log.e(
                        "NetworkException", "${result.exception.message}"
                    )
                    Toast.makeText(context, "${result.exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }.launchIn(viewModelScope)
    }

    fun connectionListener(callback:(Boolean)->Unit){

        if(checkForConnection()){
            loadClipArts()
            callback.invoke(true)
        }
        else callback.invoke(false)

    }


    private fun checkForConnection(): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

        return when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }


}

