package toniwar.projects.fakephotoapp.domain.entities

import com.google.gson.annotations.SerializedName

data class ClipArt(
    val isUri: Boolean = false,

    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("img")
    val img: String


)
