package toniwar.projects.extremecamera.presentation

import android.content.Context


class ClipArtView(
    context: Context
): androidx.appcompat.widget.AppCompatImageView(context) {


    fun move(newX: Float, newY: Float){

        x += newX
        y += newY
        invalidate()
    }

    fun changeScale(newScale: Float, pivot: PivotType){

        when(pivot){
            PivotType.PIVOT_X -> scaleX += newScale
            PivotType.PIVOT_Y -> scaleY += newScale
            PivotType.BOTH_PIVOTS ->{
                scaleX += newScale
                scaleY += newScale
            }
        }
        invalidate()


    }

    fun changeOpacity(newAlpha: Float){

        alpha += newAlpha
        invalidate()

    }

    companion object{

        enum class PivotType(){
            PIVOT_X, PIVOT_Y, BOTH_PIVOTS
        }
    }

}
