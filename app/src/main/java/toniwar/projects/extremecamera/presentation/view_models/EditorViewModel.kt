package toniwar.projects.extremecamera.presentation.view_models

import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import toniwar.projects.extremecamera.presentation.ClipArtMenu
import javax.inject.Inject

class EditorViewModel @Inject constructor(): ViewModel() {



    fun showMenu(guideline: Guideline){
        ClipArtMenu.menu(guideline){

        }

    }





}