package toniwar.projects.fakephotoapp.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import toniwar.projects.fakephotoapp.App
import toniwar.projects.fakephotoapp.databinding.FragmentEditorBinding
import toniwar.projects.fakephotoapp.di.activity_component.DaggerActivityComponent
import toniwar.projects.fakephotoapp.presentation.ClipArtsRVAdapter
import toniwar.projects.fakephotoapp.presentation.EditorMenu
import toniwar.projects.fakephotoapp.presentation.listeners.FragmentListener
import toniwar.projects.fakephotoapp.presentation.view_models.EditorViewModel
import toniwar.projects.fakephotoapp.presentation.view_models.vm_fabric.ViewModelsFabric
import javax.inject.Inject


class Editor : Fragment() {

    private var path: String? = null

    private lateinit var listener: FragmentListener

    private val binding by lazy {
        FragmentEditorBinding.inflate(layoutInflater)

    }

    private val mainComponent by lazy {
        (requireActivity().application as App).provideMainComponent()
    }

    private val component by lazy {
        DaggerActivityComponent.factory().create(mainComponent)
    }

    private val clipArtsRVAdapter by lazy {
        ClipArtsRVAdapter()
    }

    private val guideLinesSet by lazy {
        with(binding) {
            listOf(lineRight, lineLeft, line1, line2)
        }
    }



    @Inject
    lateinit var fabric: ViewModelsFabric
    private val vm by lazy {
        ViewModelProvider(this, fabric)[EditorViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentListener){
            listener = context
        }
        else throw RuntimeException("Unknown element: $context")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        EditorMenu.resetValues()
        arguments?.let {
            path = it.getString(URI_KEY)
        }
        return binding.root
    }

    @SuppressLint("SdCardPath")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectEditor(this)

        binding.apply {
            vm.setImage(photo, path?:"")

        }
        Log.d("ImagePath", path!!)
        binding.clipArtsRv.adapter = clipArtsRVAdapter
        clipArtsRVAdapter.itemListener = {
            vm.removeClipArtView(binding.containerLayout)
            vm.inlineImage(binding.containerLayout, it.img, binding.controller)
        }




        binding.apply {
            openClipArtsButton.setOnClickListener {
                vm.showMenu(guideLinesSet, EditorMenu.MenuTypes.CLIP_ARTS_LIST)

            }
            toolsButton.setOnClickListener {
                vm.showMenu(guideLinesSet, EditorMenu.MenuTypes.TOOLS)
            }
            saveImageButton.setOnClickListener {
                vm.saveImage(containerLayout){result ->
                    result?.let {
                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                        listener.openFragment(FragmentListener.Companion.ActionFlag.FINAL_RESULT, it.toString())
                    }

                }

            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow<Unit> {
                    var callback = false
                    while (!callback) {
                        vm.connectionListener {
                            callback = it
                        }
                        if (callback) {
                            getClipArtsList()
                        }
                        delay(3000)
                    }
                }.collect()
            }
        }

        val onBackClick = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                listener.openFragment(FragmentListener.Companion.ActionFlag.CAMERA_X, null)

            }
        }
        (requireActivity()).onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)

    }

    private suspend fun getClipArtsList(){
        vm.clipArtsList.collectLatest{ clipArts->
            clipArts?.let {
                clipArtsRVAdapter.loadClipArts(it.clipArtsList)

            }

        }

    }


    companion object {
        const val URI_KEY = "Uri"
        fun newInstance(arg: String? = null) = Editor().apply {
            arguments = Bundle().apply {
                putString(URI_KEY, arg)
            }
        }

    }

}

