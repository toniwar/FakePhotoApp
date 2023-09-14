package toniwar.projects.extremecamera.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.domain.entities.SampleImage

class SamplesAdapter
    : RecyclerView.Adapter<SampleImageHolder>() {

    private val samples = mutableListOf<SampleImage>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleImageHolder {
        val itemView = LayoutInflater.from(
            parent.context)
            .inflate(R.layout.sample_image_card, parent, false)
        return SampleImageHolder(itemView)
    }

    override fun getItemCount(): Int {
        return samples.size
    }

    override fun onBindViewHolder(holder: SampleImageHolder, position: Int) {
        val item = samples[position]
        holder.bind(item)
    }

    fun loadSamples(inputList: List<SampleImage>){
        samples.clear()
        inputList.forEach {
            samples.add(it)
        }
        notifyDataSetChanged()

    }
}
