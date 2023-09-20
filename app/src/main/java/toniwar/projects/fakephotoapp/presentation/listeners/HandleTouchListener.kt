package toniwar.projects.fakephotoapp.presentation.listeners

import android.view.MotionEvent
import android.view.View

class HandleTouchListener: View.OnTouchListener {

    private var dX = 0f
    private var dY = 0f
    private var posX = 0f
    private var posY = 0f


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        if(p0 == null || p1 == null) return false

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


}
