package toniwar.projects.extremecamera.presentation.view_models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CameraViewModel @Inject constructor(
): ViewModel() {

    fun showMenu(context: Context){
        Toast.makeText(context, "Iam view model!", Toast.LENGTH_SHORT).show()

    }


}
