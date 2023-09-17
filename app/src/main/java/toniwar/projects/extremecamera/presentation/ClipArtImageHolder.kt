package toniwar.projects.extremecamera.presentation

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.databinding.ClipArtImageCardBinding
import toniwar.projects.extremecamera.domain.entities.ClipArt
import java.io.IOException

class ClipArtImageHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val binding by lazy {
        ClipArtImageCardBinding.bind(view)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: ClipArt) = with(binding){
        getImage(item.img, clipArtImg)
        clipArtTitle.text = "${item.id}: ${item.title}"

    }

    private fun getImage(image: String, imageView: ImageView){

        try {
            Glide.with(itemView.context).load(image).centerCrop().into(imageView)
        }
        catch (e: IOException){
            imageView.setImageResource(R.drawable.baseline_image_not_supported_24)
        }
    }


}

