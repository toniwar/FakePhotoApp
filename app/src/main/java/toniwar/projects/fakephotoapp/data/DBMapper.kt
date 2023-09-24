package toniwar.projects.fakephotoapp.data

import toniwar.projects.fakephotoapp.data.database.ClipArtDBModel
import toniwar.projects.fakephotoapp.domain.entities.ClipArt

class DBMapper {

    fun mapToClipArtDBModelList(input: List<ClipArt>): List<ClipArtDBModel>{

        return input.map { with(it){ClipArtDBModel(id, title, img)} }

    }

    fun mapToClipArtList(input: List<ClipArtDBModel>): List<ClipArt>{
        return input.map { with(it){ClipArt(id, title, img)} }

    }


}
