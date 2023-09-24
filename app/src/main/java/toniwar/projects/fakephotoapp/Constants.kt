package toniwar.projects.fakephotoapp

import android.annotation.SuppressLint

object Constants {

    @SuppressLint("NonConstantResourceId")
    const val  TAG = "FakePhotoApp"
    const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    const val PATH_FOR_TAKE_PHOTO = "Pictures/$TAG/Photos"
    const val PATH_FOR_EDITED_IMG = "Pictures/$TAG/EditedImages"
    const val PATH_FOR_CLIP_ARTS = "Pictures/$TAG/ClipArts"
    const val CLIP_ARTS_DB_NAME = "clip_arts_db"
    const val CLIP_ARTS_TABLE_NAME = "clip_arts_table"
}
