package toniwar.projects.extremecamera.presentation.view_models

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import toniwar.projects.extremecamera.domain.entities.Failure
import toniwar.projects.extremecamera.domain.entities.NetworkException
import toniwar.projects.extremecamera.domain.entities.ClipArts
import toniwar.projects.extremecamera.domain.entities.Success
import toniwar.projects.extremecamera.domain.repositories.DataRepository
import toniwar.projects.extremecamera.domain.use_cases.data_use_cases.GetBitmapUseCase
import toniwar.projects.extremecamera.domain.use_cases.data_use_cases.LoadClipArtsUseCase
import toniwar.projects.extremecamera.domain.use_cases.data_use_cases.SaveEditedImageUseCase
import toniwar.projects.extremecamera.presentation.ClipArtView
import toniwar.projects.extremecamera.presentation.ClipArtViewController
import toniwar.projects.extremecamera.presentation.EditorMenu
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

    private val mutableClipArtsList = MutableStateFlow<ClipArts?>(null)

    val clipArtsList:StateFlow<ClipArts?> get() = mutableClipArtsList.asStateFlow()


    @SuppressLint("StaticFieldLeak")
    private var clipArtView: ClipArtView? = null

    fun showMenu(guideline: List<Guideline>, menuType: EditorMenu.MenuTypes ){
        EditorMenu.menu(guideline, menuType)

    }



    fun inlineImage(view: ViewGroup, img: String, controller: ClipArtViewController){

        clipArtView = ClipArtView(context)
        clipArtView?.let {
            Glide.with(context)
                .load(img)
                .centerInside()
                .into(it)
            view.addView(it)
            it.layoutParams = FrameLayout.LayoutParams(view.width, view.height)

            controller.setSampleView(it)
        }


    }

    fun removeClipArtView(view: ViewGroup){
        clipArtView?.let {
            clipArtView = null
            view.removeView(it)
            view.invalidate()
        }

    }

    fun setImage(view: ImageView, url: String){
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(view)
    }



    fun saveImage(view: View){
        val bitmap = getBitmapUseCase.getBitmap(view)
        bitmap?.let {
            saveEditedImageUseCase.saveEditedImage(bitmap)
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

