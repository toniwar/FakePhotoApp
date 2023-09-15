package toniwar.projects.extremecamera.presentation.view_models

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.domain.entities.Failure
import toniwar.projects.extremecamera.domain.entities.NetworkException
import toniwar.projects.extremecamera.domain.entities.Samples
import toniwar.projects.extremecamera.domain.entities.Success
import toniwar.projects.extremecamera.domain.repositories.DataRepository
import toniwar.projects.extremecamera.domain.use_cases.data_use_cases.LoadSamplesUseCase
import toniwar.projects.extremecamera.presentation.ClipArtMenu
import javax.inject.Inject

class EditorViewModel @Inject constructor(
    private val context: Context,
    private val dataRepository: DataRepository,
): ViewModel() {

    private var isVisibleMenu = false

    private val loadSamplesUseCase by lazy {
        LoadSamplesUseCase(dataRepository)
    }

    private val mutableSamplesList = MutableStateFlow<Samples?>(null)

    val samplesList:StateFlow<Samples?> get() = mutableSamplesList.asStateFlow()

    private val configuration by lazy {
        context.resources.configuration
    }

    fun showMenu(guideline: Guideline){
        ClipArtMenu.menu(guideline){
            isVisibleMenu = it

        }

    }

    fun rotateImageView(view: View){
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            view.apply {
                rotation = 90f
                scaleY = 2f
                scaleX = 2f
                invalidate()
            }
        }

    }

    fun inlineImage(view: ViewGroup, img: String){


        val imgView = ImageView(context)
        Glide.with(context)
            .load(img)
            .centerCrop()
            .into(imgView)
        view.addView(imgView)
        imgView.apply {
            layoutParams = FrameLayout.LayoutParams(view.width, view.height)

        }


    }

    private suspend fun getBitmap(uri: String): Bitmap{

            return withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .submit(100, 100)
                    .get()
            }

    }


    fun changeMenuButtonIcon(icon: (Int) -> Unit){
        icon.invoke(getIcon(isVisibleMenu))

    }

    private fun getIcon(visible: Boolean): Int{
        return if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(visible) R.drawable.baseline_keyboard_arrow_right_24
            else R.drawable.baseline_keyboard_arrow_left_24
        }

        else{
            if(visible) R.drawable.baseline_keyboard_arrow_down_24
            else R.drawable.baseline_keyboard_arrow_up_24
        }
    }

    private fun loadSamples(){

        loadSamplesUseCase.loadSamples().onEach {result->
            when(result){
                is Success<*> -> {
                    if(result.samples is Samples)
                        mutableSamplesList.value = result.samples
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
            loadSamples()
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

