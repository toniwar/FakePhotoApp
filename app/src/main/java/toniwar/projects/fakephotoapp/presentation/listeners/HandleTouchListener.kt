package toniwar.projects.fakephotoapp.presentation.listeners

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.max
import kotlin.math.min

class HandleTouchListener(
    context: Context
    ): View.OnTouchListener {

    private var dX = 0f
    private var dY = 0f
    private var posX = 0f
    private var posY = 0f

    private var scaleFactor = 1f

    private val scaleListener = object : ScaleGestureDetector
    .SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            scaleFactor = max(0.1f, min(scaleFactor, 5.0f))

            return true
        }

    }

    private val scaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        if(p0 == null || p1 == null) return false

        if(scaleDetector.onTouchEvent(p1)){
            scaleView(p0)
        }


        when(p1.action){
            MotionEvent.ACTION_DOWN ->{
                dX = p0.x - p1.rawX
                dY = p0.y - p1.rawY
            }

            MotionEvent.ACTION_MOVE ->{
                posX = p1.rawX + dX
                posY = p1.rawY + dY
                moveView(p0)

            }

        }


        return true
    }

    private fun moveView(view: View){
        view.animate()
            .x(posX)
            .y(posY)
            .setDuration(0)
            .start()
    }

    private fun scaleView(view: View){
        view.animate()
            .scaleX(scaleFactor)
            .scaleY(scaleFactor)
            .setDuration(0)
            .start()

    }

}
