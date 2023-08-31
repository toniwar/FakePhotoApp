package toniwar.projects.extreamcamera.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import toniwar.projects.extreamcamera.R
import toniwar.projects.extreamcamera.databinding.FragmentParanormalBinding


class Paranormal : Fragment() {

    private val binding by lazy{
        FragmentParanormalBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


}