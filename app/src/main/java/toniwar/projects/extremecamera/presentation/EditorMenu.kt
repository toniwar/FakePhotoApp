package toniwar.projects.extremecamera.presentation

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.Guideline

object EditorMenu {

    private var clipArtsRVVisibility = false
    private var toolsVisibility = false
    private var rightLinePercent = 0.90f
    private var leftLinePercent = 0.10f
    private var bottomLine1Percent = 1f
    private var bottomLine2Percent = 1f


    fun resetValues(){
        clipArtsRVVisibility = false
        toolsVisibility = false
        rightLinePercent = 0.90f
        leftLinePercent = 0.10f
        bottomLine1Percent = 1f
        bottomLine2Percent = 1f
    }


    fun menu(guidelines: List<Guideline>, action: MenuTypes) {

        when (action) {
            MenuTypes.TOOLS -> {
                toolsVisibility = !toolsVisibility
                startAnimation(hashMapOf(
                    LEFT_LINE_ANIMATOR_HOLDER to guidelines[1],
                    BOTTOM_LINE_2_ANIMATOR_HOLDER to guidelines[3]
                    ),
                    toolsVisibility)
                clipArtsRVVisibility = false

                startAnimation(hashMapOf(
                    RIGHT_LINE_ANIMATOR_HOLDER to guidelines[0],
                    BOTTOM_LINE_1_ANIMATOR_HOLDER to guidelines[2]
                ),
                    clipArtsRVVisibility)
            }

            MenuTypes.CLIP_ARTS_LIST -> {
                clipArtsRVVisibility = !clipArtsRVVisibility

                startAnimation(hashMapOf(
                    RIGHT_LINE_ANIMATOR_HOLDER to guidelines[0],
                    BOTTOM_LINE_1_ANIMATOR_HOLDER to guidelines[2]
                ),
                    clipArtsRVVisibility)

                toolsVisibility = false

                startAnimation(hashMapOf(
                    LEFT_LINE_ANIMATOR_HOLDER to guidelines[1],
                    BOTTOM_LINE_2_ANIMATOR_HOLDER to guidelines[3]
                ),
                    toolsVisibility)

            }
        }

    }

    private fun startAnimation(

        guidelinesHashMap: HashMap<String, Guideline>,
        isOpen: Boolean

    ){
        val guidelines: MutableList<Guideline>  = mutableListOf()
        guidelinesHashMap.forEach { guidelines.add(it.value) }
        val propertyList: MutableList<PropertyValuesHolder> = mutableListOf()
        guidelinesHashMap
            .forEach{propertyList.add(setAnimatorHolder(it.key, isOpen))
        }


        ValueAnimator().apply {
            duration = 300
            interpolator = LinearInterpolator()
            setValues(*propertyList.toTypedArray())

            addUpdateListener {
                guidelines.forEachIndexed {i, p ->
                    p.setGuidelinePercent(
                        getAnimatedValue(propertyList[i].propertyName) as Float)
                    p.invalidate()
                }
            }
        }.start()
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
                endValue = if (visibility) 0.0f else 0.90f
                rightLinePercent = endValue
            }

            LEFT_LINE_ANIMATOR_HOLDER -> {
                startValue = leftLinePercent
                endValue = if(visibility) 1.0f else 0.10f
                leftLinePercent = endValue
            }

            BOTTOM_LINE_1_ANIMATOR_HOLDER ->{
                startValue = bottomLine1Percent
                endValue = if (visibility) 0.5f else 1.0f
                bottomLine1Percent = endValue
            }

            BOTTOM_LINE_2_ANIMATOR_HOLDER ->{
                startValue = bottomLine2Percent
                endValue = if (visibility) 0.6f else 1.0f
                bottomLine2Percent = endValue
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
