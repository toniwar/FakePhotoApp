package toniwar.projects.fakephotoapp.presentation.view_models

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.domain.entities.ClipArt
import toniwar.projects.fakephotoapp.domain.entities.Failure
import toniwar.projects.fakephotoapp.domain.entities.UploadException
import toniwar.projects.fakephotoapp.domain.entities.ClipArts
import toniwar.projects.fakephotoapp.domain.entities.Success
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.GetBitmapUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.LoadClipArtsUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.SaveImageUseCase
import toniwar.projects.fakephotoapp.presentation.ClipArtView
import toniwar.projects.fakephotoapp.presentation.ClipArtViewController
import toniwar.projects.fakephotoapp.presentation.EditorMenu
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.InlineImageToViewUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.ReadFromSharedPrefsUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.SaveClipArtsInDBUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.SetImageToViewUseCase
import toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases.WriteToSharedPrefsUseCase
import java.lang.Exception
import javax.inject.Inject

class EditorViewModel @Inject constructor(
    private val context: Context,
    private val dataRepository: DataRepository,
): ViewModel() {


    private val loadClipArtsUseCase by lazy {
        LoadClipArtsUseCase(dataRepository)
    }

    private val saveImageUseCase by lazy {
        SaveImageUseCase(dataRepository)
    }

    private val getBitmapUseCase by lazy {
        GetBitmapUseCase(dataRepository)
    }

    private val setImageToViewUseCase by lazy {
        SetImageToViewUseCase(dataRepository)
    }

    private val inlineImageToViewUseCase by lazy{
        InlineImageToViewUseCase(dataRepository)
    }

    private val writeToSharedPrefsUseCase by lazy {
        WriteToSharedPrefsUseCase(dataRepository)
    }

    private val readFromSharedPrefsUseCase by lazy {
        ReadFromSharedPrefsUseCase(dataRepository)
    }


    private val saveClipArtsInDBUseCase by lazy {
        SaveClipArtsInDBUseCase(dataRepository)
    }


    private val mutableClipArtsList =
        MutableStateFlow<List<ClipArt>?>(null)

    private var isHasRecordInDB =
        readFromSharedPrefsUseCase
            .readFromSharedPrefs<Boolean>(Constants.PrefDataType.IS_RECORDED_IN_DB)

    val clipArtsList:StateFlow<List<ClipArt>?> get() = mutableClipArtsList.asStateFlow()


    @SuppressLint("StaticFieldLeak")
    private var clipArtView: ClipArtView? = null

    private val job = CoroutineScope(Dispatchers.IO)

    fun showMenu(guideline: List<Guideline>, menuType: EditorMenu.MenuTypes ){
        EditorMenu.menu(guideline, menuType)

    }

    fun removeClipArtView(view: ViewGroup){
        clipArtView?.let {
            clipArtView = null
            view.removeView(it)
            view.invalidate()
        }

    }


    fun <T> setImage(view: ImageView, source: T){
        setImageToViewUseCase.setImageToView(view, source)
    }

    fun <T> inlineImage(view: ViewGroup, source: T, controller: ClipArtViewController){

        inlineImageToViewUseCase.inlineImageToView(view, source)

        clipArtView?.let {
            controller.setClipArtView(it)
        }
    }



    fun <T> saveImage(
        source: T,
        imgFormat: Format = Format.JPEG,
        id: Int? = null,
        uri: (Uri?)-> Unit){
        val bitmap = getBitmapUseCase.getBitmap(source)
        bitmap?.let {
            uri.invoke(
                when(imgFormat){
                    Format.JPEG ->{
                        saveImageUseCase
                            .saveImage(
                                bitmap,
                                Constants.PATH_FOR_EDITED_IMG,
                                null,
                                Bitmap.CompressFormat.JPEG,
                                Constants.IMAGE_JPEG
                            )
                    }
                    Format.PNG ->{
                        saveImageUseCase.saveImage(
                            bitmap,
                            Constants.PATH_FOR_CLIP_ARTS,
                            id,
                            Bitmap.CompressFormat.PNG,
                            Constants.IMAGE_PNG
                        )

                    }
                }
            )
        }


    }


    private fun loadClipArts(isLocal: Boolean){

        loadClipArtsUseCase.loadClipArts(isLocal).onEach { result->
            when(result){
                is Success<*> -> {
                    if(result.clipArts is ClipArts){
                        val newList = mutableListOf<ClipArt>()
                        result.clipArts.clipArtsList.forEach {
                            try {
                                newList.add(it)
                            }
                            catch (e: Exception){
                                Log.e("Read result in EditorVM", e.message.toString())
                            }
                        }
                        mutableClipArtsList.value = newList
                        saveClipArtsInLocalStorage(newList)
                        if(isHasRecordInDB == null || isHasRecordInDB == false)
                            writeToSharedPrefsUseCase
                                .writeToSharedPrefs(
                                    Constants.PrefDataType.IS_RECORDED_IN_DB, true
                                )
                    }

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

                is UploadException -> {
                    Log.e(
                        "UploadException", "${result.exception.message}"
                    )
                    Toast.makeText(context, "${result.exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }.launchIn(job)
    }

    fun connectionListener(isConnect:(Boolean)->Unit){
        var record  = false
        isHasRecordInDB?.let{
            record = it
        }

        if(checkForConnection()){
            loadClipArts(false)
            isConnect.invoke(true)
        }

        else{
            if(record){
                loadClipArts(true)
                isConnect.invoke(true)
            }

            else isConnect.invoke(false)
        }

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

    private fun saveClipArtsInLocalStorage(clipArts: List<ClipArt>){

        if(checkSize() == 0) {
            val newClipArts = mutableListOf<ClipArt>()
            clipArts.forEach {clipArt->
                saveImage(clipArt.img, Format.PNG, clipArt.id){
                    val newClipArt = clipArt.copy(img = it.toString())
                    newClipArts.add(newClipArt)
                }

            }
            saveClipArtsInDBUseCase.saveClipArtsInDB(newClipArts)
            writeToSharedPrefsUseCase
                .writeToSharedPrefs(Constants.PrefDataType.SIZE, newClipArts.size)

        }

    }

    private fun checkSize(): Int{
        val size = readFromSharedPrefsUseCase
            .readFromSharedPrefs<Int>(Constants.PrefDataType.SIZE)
        size?.let{
            return it
        }
        return 0

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    companion object ImageType{
        enum class Format{
            JPEG, PNG
        }
    }


}

