package toniwar.projects.extreamcamera.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import toniwar.projects.extreamcamera.R
import toniwar.projects.extreamcamera.presentation.fragments.Menu
import toniwar.projects.extreamcamera.presentation.fragments.NaturalDisasters
import toniwar.projects.extreamcamera.presentation.fragments.Paranormal

class MainActivity : AppCompatActivity(), FragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openFragment(flag: FragmentListener.Companion.ActionFlag) {

        when(flag){
            FragmentListener.Companion.ActionFlag.MENU->openFragment(Menu())
            FragmentListener.Companion.ActionFlag.NATURAL_DISASTERS->openFragment(NaturalDisasters())
            FragmentListener.Companion.ActionFlag.PARANORMAL->openFragment(Paranormal())
        }

    }


    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("$fragment")
            .commit()
    }
}