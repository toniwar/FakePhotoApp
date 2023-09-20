package toniwar.projects.fakephotoapp.presentation

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideProvider @Inject constructor(val context: Context) {


    fun <T> setImageToView(view: ImageView, uri: T){
        Glide.with(context)
            .load(uri)
            .centerInside()
            .into(view)
    }

    fun <T> inlineClipArtView(view: ViewGroup, uri: T): ClipArtView{

        val clipArtView = ClipArtView(context)
        clipArtView.let {
            Glide.with(context)
                .load(uri)
                .centerInside()
                .into(it)
            view.addView(it)
            it.layoutParams = FrameLayout.LayoutParams(view.width, view.height)
        }

        return clipArtView


    }

}
