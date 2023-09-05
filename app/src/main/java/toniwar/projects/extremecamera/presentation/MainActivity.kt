package toniwar.projects.extremecamera.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import toniwar.projects.extremecamera.R
import toniwar.projects.extremecamera.presentation.fragments.CameraX
import toniwar.projects.extremecamera.presentation.fragments.Editor
import toniwar.projects.extremecamera.presentation.fragments.Gallery
import toniwar.projects.extremecamera.presentation.fragments.Menu


class MainActivity : AppCompatActivity(), FragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openFragment(flag: FragmentListener.Companion.ActionFlag, arg: String?) {

        when(flag){
            FragmentListener.Companion.ActionFlag.MENU->openFragment(Menu())
            FragmentListener.Companion.ActionFlag.CAMERA_X->openFragment(CameraX())
            FragmentListener.Companion.ActionFlag.GALLERY->openFragment(Gallery())
            FragmentListener.Companion.ActionFlag.EDITOR->openFragment(Editor.newInstance(arg))
        }

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("$fragment")
            .commit()
    }


}