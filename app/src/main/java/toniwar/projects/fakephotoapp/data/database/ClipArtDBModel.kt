package toniwar.projects.fakephotoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import toniwar.projects.fakephotoapp.Constants


@Entity(tableName = Constants.CLIP_ARTS_TABLE_NAME)
data class ClipArtDBModel (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val img: String
)

