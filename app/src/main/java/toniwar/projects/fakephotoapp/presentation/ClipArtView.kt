package toniwar.projects.fakephotoapp.presentation


import android.content.Context
import android.widget.FrameLayout
import toniwar.projects.fakephotoapp.presentation.listeners.HandleTouchListener



class ClipArtView(
    context: Context
): androidx.appcompat.widget.AppCompatImageView(context) {

    private val listener by lazy {
        HandleTouchListener(context)
    }
    init {
        layoutParams = FrameLayout.LayoutParams(width, height)
        setOnTouchListener(listener)

    }

    fun move(newX: Float, newY: Float){
        animate()
            .x(x + newX)
            .y(y + newY)
            .setDuration(0)
            .start()

    }

    fun changeOpacity(newAlpha: Float){

        if(alpha + newAlpha !in 0f..1.0f) return
        alpha += newAlpha
        invalidate()

    }

    fun changeScale(w: Float, h: Float){
        animate()
            .scaleX(scaleX + w)
            .scaleY(scaleY + h)
            .setDuration(0)
            .start()
    }



}


