package toniwar.projects.extremecamera.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.databinding.ClipArtViewControllerBinding


class ClipArtViewController @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null

): FrameLayout(context, attributeSet) {
    private var isLocked = true
    init {
        initializeViews()
    }
    private lateinit var binding: ClipArtViewControllerBinding



    private var clipArtView: ClipArtView? = null



    private fun initializeViews(){

        binding = ClipArtViewControllerBinding.inflate(LayoutInflater.from(context), this, true)

    }

    fun setSampleView(view: ClipArtView){
        clipArtView = view
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.apply {

            lockOpenButton.setOnClickListener {
             isLocked = !isLocked
             if(!isLocked) (it as ImageView).setImageResource(R.drawable.baseline_lock_open_24)
                else (it as ImageView).setImageResource(R.drawable.baseline_lock_24)
                invalidate()
            }

            rightButton.setOnClickListener {
                arrowClickListener(it)
            }
            leftButton.setOnClickListener {
                arrowClickListener(it)
            }
            upButton.setOnClickListener {
                arrowClickListener(it)
            }
            downButton.setOnClickListener {
                arrowClickListener(it)
            }

            alphaIncrementButton.setOnClickListener {
                clipArtView?.changeOpacity(0.1f)
            }
            alphaDecrementButton.setOnClickListener {
                clipArtView?.changeOpacity(-0.1f)
            }

        }



    }
    private fun arrowClickListener(btn: View){
        binding.apply {
            clipArtView?.let {
                when(btn){
                    rightButton -> {
                        if(isLocked) it.move(10f,0f)
                        else it.changeScale(0.1f,0f)

                    }

                    leftButton -> {
                        if(isLocked) it.move(-10f,0f)
                        else it.changeScale(-0.1f,0f)

                    }

                    upButton -> {
                        if(isLocked) it.move(0f,-10f)
                        else it.changeScale(0f,-0.1f)
                    }

                    downButton ->{
                        if(isLocked) it.move(0f,10f)
                        else it.changeScale(0f, 0.1f)

                    }
                }

            }
        }



    }



}
