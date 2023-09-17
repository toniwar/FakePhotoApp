package toniwar.projects.extremecamera.domain.entities

import com.google.gson.annotations.SerializedName

data class ClipArts(
    @field:SerializedName("samples")
    val clipArtsList: List<ClipArt>
)
