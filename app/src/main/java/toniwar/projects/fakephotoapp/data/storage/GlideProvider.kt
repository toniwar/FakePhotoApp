package toniwar.projects.fakephotoapp.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toniwar.projects.fakephotoapp.presentation.ClipArtView
import javax.inject.Inject

class GlideProvider @Inject constructor(val context: Context) {


    fun <T> setImageToView(view: ImageView, source: T){
        Glide.with(context)
            .load(source)
            .centerInside()
            .into(view)
    }

    fun <T> inlineClipArtView(view: ViewGroup, source: T): View{

        val clipArtView = ClipArtView(context)
        clipArtView.let {
            Glide.with(context)
                .load(source)
                .centerInside()
                .into(it)
            view.addView(it)
            it.layoutParams = FrameLayout.LayoutParams(view.width, view.height)
        }

        return clipArtView

    }


    fun bitmapFromPath(path: String?, callback: (Bitmap?) -> Unit){

        if(path == null) return
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(path)
                .submit()
                .get()

            callback.invoke(bitmap)
        }

    }

}
