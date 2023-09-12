package toniwar.projects.extremecamera.presentation.view_models

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.ViewModel
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.presentation.ClipArtMenu
import javax.inject.Inject

class EditorViewModel @Inject constructor(
    private val context: Context
): ViewModel() {

    private var isVisibleMenu = false



    fun showMenu(guideline: Guideline){
        ClipArtMenu.menu(guideline){
            isVisibleMenu = it

        }

    }

    fun changeMenuButtonIcon(icon: (Int) -> Unit){
        icon.invoke(getIcon(context.resources.configuration, isVisibleMenu))

    }

    private fun getIcon(configuration: Configuration, visible: Boolean): Int{
        Toast.makeText(context, "getIcon()", Toast.LENGTH_SHORT).show()
        return if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(visible) R.drawable.baseline_keyboard_arrow_right_24
            else R.drawable.baseline_keyboard_arrow_left_24
        }

        else{
            if(visible) R.drawable.baseline_keyboard_arrow_down_24
            else R.drawable.baseline_keyboard_arrow_up_24
        }
    }

}
