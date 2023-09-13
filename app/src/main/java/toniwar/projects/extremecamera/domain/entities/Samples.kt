package toniwar.projects.extremecamera.domain.entities

import com.google.gson.annotations.SerializedName

data class Samples(
    @field:SerializedName("samples")
    val samples: List<SampleImage>
)
