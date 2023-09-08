package toniwar.projects.extremecamera.presentation.view_models

import android.view.View
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EditorViewModel @Inject constructor(): ViewModel() {

    fun showMenu(view: View){
        view.translationX = -100f

    }

    fun hideMenu(){


    }


}