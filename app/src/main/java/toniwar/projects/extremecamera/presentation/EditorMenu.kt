package toniwar.projects.extremecamera.presentation

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.Guideline

object EditorMenu {

    private var clipArtsRVVisibility = false
    private var toolsVisibility = false
    private var rightLinePercent = 1f
    private var leftLinePercent = 0f
    private var bottomLine1Percent = 1f
    private var bottomLine2Percent = 1f


    fun menu(guidelines: List<Guideline>, action: MenuTypes) {
        menuAnimation(guidelines, action)

    }

    private fun menuAnimation(guidelines: List<Guideline>, action: MenuTypes) {


        when (action) {
            MenuTypes.TOOLS -> {

                toolsVisibility = !toolsVisibility
                clipArtsRVVisibility = false
            }

            MenuTypes.CLIP_ARTS_LIST -> {
                clipArtsRVVisibility = !clipArtsRVVisibility
                toolsVisibility = false

            }
        }


        val rightLineAnimatorHolder = setAnimatorHolder(RIGHT_LINE_ANIMATOR_HOLDER,
            clipArtsRVVisibility)

        val leftLineAnimatorHolder = setAnimatorHolder(LEFT_LINE_ANIMATOR_HOLDER,
            toolsVisibility)

        val bottomLine1AnimatorHolder = setAnimatorHolder(
            BOTTOM_LINE_1_ANIMATOR_HOLDER, clipArtsRVVisibility)

        val bottomLine2AnimatorHolder = setAnimatorHolder(
            BOTTOM_LINE_2_ANIMATOR_HOLDER, toolsVisibility)


        val animator = ValueAnimator().apply {
            duration = 300
            interpolator = LinearInterpolator()

            setValues(
                rightLineAnimatorHolder,
                leftLineAnimatorHolder,
                bottomLine1AnimatorHolder,
                bottomLine2AnimatorHolder
            )
            addUpdateListener {
                rightLinePercent = getAnimatedValue(RIGHT_LINE_ANIMATOR_HOLDER) as Float
                guidelines[0].setGuidelinePercent(rightLinePercent)
                leftLinePercent = getAnimatedValue(LEFT_LINE_ANIMATOR_HOLDER) as Float
                guidelines[1].setGuidelinePercent(leftLinePercent)
                bottomLine1Percent = getAnimatedValue(BOTTOM_LINE_1_ANIMATOR_HOLDER) as Float
                guidelines[2].setGuidelinePercent(bottomLine1Percent)
                bottomLine2Percent = getAnimatedValue(BOTTOM_LINE_2_ANIMATOR_HOLDER) as Float
                guidelines[3].setGuidelinePercent(bottomLine2Percent)

                guidelines.map { it.invalidate() }

            }

        }
        animator.start()


    }

    private fun setAnimatorHolder(
        holderName: String,
        visibility: Boolean
    ): PropertyValuesHolder {
        var startValue = 0f
        var endValue = 0f
        when (holderName) {
            RIGHT_LINE_ANIMATOR_HOLDER -> {
                startValue = rightLinePercent
                endValue = if (!visibility) 0.5f else 1.0f
            }

            LEFT_LINE_ANIMATOR_HOLDER -> {
                startValue = leftLinePercent
                endValue = if(!visibility) 0.5f else 0.0f
            }

            BOTTOM_LINE_1_ANIMATOR_HOLDER ->{
                startValue = bottomLine1Percent
                endValue = if (!visibility) 0.5f else 1.0f
            }

            BOTTOM_LINE_2_ANIMATOR_HOLDER ->{
                startValue = bottomLine2Percent
                endValue = if (!visibility) 0.3f else 1.0f
            }

        }

        return PropertyValuesHolder.ofFloat(holderName, startValue, endValue)
    }

    enum class MenuTypes {
        TOOLS, CLIP_ARTS_LIST
    }


    private const val RIGHT_LINE_ANIMATOR_HOLDER = "right line_animator_holder"
    private const val LEFT_LINE_ANIMATOR_HOLDER = "left line animator holder"
    private const val BOTTOM_LINE_1_ANIMATOR_HOLDER = "bottom line 1 animator holder"
    private const val BOTTOM_LINE_2_ANIMATOR_HOLDER = "bottom line 2 animator holder"


}
