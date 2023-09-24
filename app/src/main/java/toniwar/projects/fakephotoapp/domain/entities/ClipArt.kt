package toniwar.projects.fakephotoapp.domain.entities

import com.google.gson.annotations.SerializedName

data class ClipArt(
    val id: Int,
    val title: String,
    @field:SerializedName("img")
    val img: String
)
