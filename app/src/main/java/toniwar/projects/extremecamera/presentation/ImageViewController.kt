package toniwar.projects.extremecamera.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.databinding.SampleImageViewControllerBinding

class ImageViewController @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null

): FrameLayout(context, attributeSet) {
    init {
        initializeView()
    }

    private val binding by lazy{
        SampleImageViewControllerBinding
            .bind(this)
    }

    private var sampleView: SampleImageView? = null

    private var isLocked = true

    private fun initializeView(){
        inflate(context, R.layout.sample_image_view_controller, this)

    }

    fun setSampleView(view: SampleImageView){
        sampleView = view
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.apply {

        }
    }
}