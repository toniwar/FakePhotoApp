package toniwar.projects.extremecamera.presentation

import android.content.Context
import android.widget.FrameLayout


class ClipArtView(
    context: Context
): androidx.appcompat.widget.AppCompatImageView(context) {
    init {
        layoutParams = FrameLayout.LayoutParams(width, height)
    }



    fun move(newX: Float, newY: Float){

        x += newX
        y += newY
        invalidate()
    }



    fun changeOpacity(newAlpha: Float){

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

