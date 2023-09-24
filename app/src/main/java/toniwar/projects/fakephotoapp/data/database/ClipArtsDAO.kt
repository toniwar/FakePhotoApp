package toniwar.projects.fakephotoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import toniwar.projects.fakephotoapp.Constants


@Dao
interface ClipArtsDAO {


    @Query("SELECT * FROM ${Constants.CLIP_ARTS_TABLE_NAME}")
    fun getClipArts(): List<ClipArtDBModel>?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addClipArt(clipArtDBModel: ClipArtDBModel)

    @Query("SELECT * FROM ${Constants.CLIP_ARTS_TABLE_NAME} WHERE id=:clipArtId")
    fun getClipArt(clipArtId: Int): ClipArtDBModel?

}
