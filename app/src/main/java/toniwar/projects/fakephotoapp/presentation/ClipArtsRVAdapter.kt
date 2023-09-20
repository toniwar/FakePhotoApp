package toniwar.projects.fakephotoapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import toniwar.projects.fakephotoapp.R
import toniwar.projects.fakephotoapp.domain.entities.ClipArt

class ClipArtsRVAdapter
    : RecyclerView.Adapter<ClipArtImageHolder>() {

    private val clipArtsList = mutableListOf<ClipArt>()
    var itemListener: ((ClipArt)->Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipArtImageHolder {
        val itemView = LayoutInflater.from(
            parent.context)
            .inflate(R.layout.clip_art_image_card, parent, false)
        return ClipArtImageHolder(itemView)
    }

    override fun getItemCount(): Int {
        return clipArtsList.size
    }

    override fun onBindViewHolder(holder: ClipArtImageHolder, position: Int) {
        val item = clipArtsList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            itemListener?.invoke(item)
        }
    }

    fun loadClipArts(inputList: List<ClipArt>){
        clipArtsList.clear()
        inputList.forEach {
            clipArtsList.add(it)
        }
        notifyDataSetChanged()

    }
}
