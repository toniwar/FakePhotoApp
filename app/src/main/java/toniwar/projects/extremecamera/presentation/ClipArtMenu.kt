package toniwar.projects.extremecamera.presentation

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.Guideline

object ClipArtMenu {

    private var isVisible = false
    private var guideLinePercent = 1f

    fun menu(guideline: Guideline, callback: (Boolean) -> Unit){
        menuAnimation(guideline)

        isVisible = !isVisible
        callback.invoke(isVisible)

    }

    private fun menuAnimation(guideline: Guideline){

        val animatorHolder = if(!isVisible) PropertyValuesHolder
            .ofFloat("menu", guideLinePercent, 0.5f)
        else PropertyValuesHolder.ofFloat("menu", guideLinePercent, 1.0f)

        val animator = ValueAnimator().apply {
            duration = 300
            interpolator = LinearInterpolator()
            setValues(animatorHolder)
            addUpdateListener {
                guideLinePercent  = getAnimatedValue("menu") as Float
                guideline.setGuidelinePercent(guideLinePercent)
                guideline.invalidate()
            }

        }
        animator.start()


    }


}