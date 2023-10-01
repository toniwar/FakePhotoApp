package toniwar.projects.fakephotoapp.presentation.view_models

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        clipArtView = inlineImageToViewUseCase.inlineImageToView(view, source) as ClipArtView
        clipArtView?.let {
            controller.setClipArtView(it)
        }
    }
    fun screenShot(view: View, uri: (Uri)-> Unit){
        getBitmapArray(view){bitmaps ->
            saveImages(bitmaps){ uriList->
                uriList.first()?.let { uri.invoke(it) }
            }
        }

    }
    private fun <T> getBitmapArray(source: T, bitmaps: (List<Bitmap?>) -> Unit ){
        job.launch {
            getBitmapUseCase.getBitmap(source).collect{bitmaps.invoke(it)}
        }
    }
    private fun saveImages(
        source: List<Bitmap?>,
        imgFormat: Format = Format.JPEG,
        idList: List<Int>? = null,
        uri: (List<Uri?>)-> Unit){

        val uriList = mutableListOf<Uri?>()
        when(imgFormat){
            Format.JPEG ->{
                source.forEach {
                    uriList.add(
                        saveImageUseCase.saveImage(
                            it,
                            Constants.PATH_FOR_EDITED_IMG,
                            null,
                             Bitmap.CompressFormat.JPEG,
                            Constants.IMAGE_JPEG
                    ))
                }
            }
            Format.PNG ->{
                source.forEachIndexed {index, item->
                    uriList.add(
                        saveImageUseCase.saveImage(
                            item,
                            Constants.PATH_FOR_CLIP_ARTS,
                            idList?.get(index),
                            Bitmap.CompressFormat.PNG,
                            Constants.IMAGE_PNG
                        ))
                }
            }
        }
        uri.invoke(uriList)
    }
    private fun loadClipArts(isLocal: Boolean){
        Log.d("EditorVM", "Load ClipArts, isLocal = $isLocal")

        loadClipArtsUseCase.loadClipArts(isLocal).onEach { result->
            when(result){
                is Success<*> -> {
                    if(result.clipArts is ClipArts){
                        Log.d("EditorVM", "Result is ClipArts")
                        val newList = mutableListOf<ClipArt>()
                        result.clipArts.clipArtsList.forEach {
                            try {
                                newList.add(it)
                            }
                            catch (e: Exception){
                                Log.e("EditorVM", e.message.toString())
                            }
                        }
                        mutableClipArtsList.value = newList
                        if(!isLocal) {
                            saveClipArtsInLocalStorage(newList.toList())
                            if(isHasRecordInDB == null || isHasRecordInDB == false)
                                writeToSharedPrefsUseCase
                                    .writeToSharedPrefs(
                                        Constants.PrefDataType.IS_RECORDED_IN_DB, true)
                        }

                    }
                }
                is Failure -> {
                    Log.e(
                        "EditorVM", "${result.errorCode}: ${result.errorMessage}"
                    )
                    Toast.makeText(context,
                        "${result.errorCode}: ${result.errorMessage}",
                        Toast.LENGTH_SHORT)
                        .show()
                }

                is UploadException -> {
                    Log.e(
                        "EditorVM", "${result.exception.message}"
                    )
                    Toast.makeText(context, "${result.exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }.launchIn(job)
    }

    fun connectionListener(isConnect:(Boolean)->Unit){
        if(checkForConnection()){
            loadClipArts(false)
            isConnect.invoke(true)
        }

        else{
            if(isHasRecordInDB != null && isHasRecordInDB==true){
                Log.d("EditorVM", "Try to get data from DB")
                isConnect.invoke(true)
                loadClipArts(true)
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
        val paths = clipArts.map { it.img }
        val idList = clipArts.map { it.id }
        val newClipArts = mutableListOf<ClipArt>()
        getBitmapArray(paths){
            saveImages(it, Format.PNG, idList,){uriList ->
                uriList.forEachIndexed { index, uri ->
                    val newClipArt = clipArts[index].copy(img = uri.toString())
                    newClipArts.add(newClipArt)
                }
                saveClipArtsInDBUseCase.saveClipArtsInDB(newClipArts.toList())
                writeToSharedPrefsUseCase
                    .writeToSharedPrefs(Constants.PrefDataType.SIZE, newClipArts.size)

            }
        }

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

