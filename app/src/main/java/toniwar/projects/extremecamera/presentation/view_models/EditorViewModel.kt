package toniwar.projects.extremecamera.presentation.view_models

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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




    fun showMenu(guideline: Guideline){
        ClipArtMenu.menu(guideline){
            isVisibleMenu = it

        }

    }

    fun changeMenuButtonIcon(icon: (Int) -> Unit){
        icon.invoke(getIcon(context.resources.configuration, isVisibleMenu))

    }

    private fun getIcon(configuration: Configuration, visible: Boolean): Int{
        return if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(visible) R.drawable.baseline_keyboard_arrow_right_24
            else R.drawable.baseline_keyboard_arrow_left_24
        }

        else{
            if(visible) R.drawable.baseline_keyboard_arrow_down_24
            else R.drawable.baseline_keyboard_arrow_up_24
        }
    }

    fun loadSamples(){
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

}

