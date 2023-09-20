package toniwar.projects.fakephotoapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import toniwar.projects.fakephotoapp.databinding.FragmentMenuBinding
import toniwar.projects.fakephotoapp.presentation.listeners.FragmentListener


class Menu : Fragment() {

    private lateinit var listener: FragmentListener

    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentListener){
            listener = context
        }
        else throw RuntimeException("Unknown element: $context")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            menuTakePhotoBtn.setOnClickListener {
                listener.openFragment(FragmentListener.Companion.ActionFlag.CAMERA_X, null)
            }

            menuChoosePhotoBtn.setOnClickListener {
                listener.openFragment(FragmentListener.Companion.ActionFlag.GALLERY, null)
            }
        }
    }


}