package toniwar.projects.fakephotoapp.presentation


import android.content.Context
import android.widget.FrameLayout
import toniwar.projects.fakephotoapp.presentation.listeners.HandleTouchListener


class ClipArtView(
    context: Context
): androidx.appcompat.widget.AppCompatImageView(context) {

    private val listener by lazy {
        HandleTouchListener()
    }

    init {
        layoutParams = FrameLayout.LayoutParams(width, height)
        setOnTouchListener(listener)
    }



    fun move(newX: Float, newY: Float){
        x += newX
        y += newY
        invalidate()

    }



    fun changeOpacity(newAlpha: Float){

        if(alpha + newAlpha !in 0f..1.0f) return
        alpha += newAlpha
        invalidate()

    }

    fun changeScale(w: Float, h: Float){
        val oldScaleX = scaleX
        scaleX = oldScaleX + w

        val oldScaleY = scaleY
        scaleY = oldScaleY + h
    }



}


