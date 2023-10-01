package toniwar.projects.fakephotoapp.data

import toniwar.projects.fakephotoapp.data.database.ClipArtDBModel
import toniwar.projects.fakephotoapp.domain.entities.ClipArt
import toniwar.projects.fakephotoapp.domain.entities.ClipArts

class DBMapper {

    fun mapToClipArtDBModelList(input: List<ClipArt>): List<ClipArtDBModel>{

        return input.map { with(it){ClipArtDBModel(id, title, img)} }

    }

    fun mapToClipArtList(input: List<ClipArtDBModel>): ClipArts{
        val list = input.map { with(it){ClipArt(true, id, title, img)} }
        return ClipArts(list)

    }


}
