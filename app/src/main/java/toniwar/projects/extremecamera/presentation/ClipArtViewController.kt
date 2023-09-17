package toniwar.projects.extremecamera.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import toniwar.projects.extremecamera.R


class ClipArtViewController @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null

): FrameLayout(context, attributeSet) {
    init {
        initializeView()
    }



    private var clipArtView: ClipArtView? = null

    private var isLocked = true

    private fun initializeView(){
        inflate(context, R.layout.clip_art_view_controller, this)

    }

    fun setSampleView(view: ClipArtView){
        clipArtView = view
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }
}