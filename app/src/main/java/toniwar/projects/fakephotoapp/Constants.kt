package toniwar.projects.fakephotoapp

import android.annotation.SuppressLint

object Constants {

    @SuppressLint("NonConstantResourceId")
    const val  TAG = "FakePhotoApp"
    const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    const val PATH_FOR_TAKE_PHOTO = "Pictures/$TAG-Image"
    const val PATH_FOR_EDITED_IMG = "Pictures/$TAG-EditedImage"
}
