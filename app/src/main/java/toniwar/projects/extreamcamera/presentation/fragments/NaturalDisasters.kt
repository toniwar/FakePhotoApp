package toniwar.projects.extreamcamera.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import toniwar.projects.extreamcamera.App
import toniwar.projects.extreamcamera.databinding.FragmentNaturalDisastersBinding
import toniwar.projects.extreamcamera.di.DaggerActivityComponent
import toniwar.projects.extreamcamera.presentation.view_models.CameraViewModel
import toniwar.projects.extreamcamera.presentation.view_models.vm_fabric.ViewModelsFabric
import javax.inject.Inject


class NaturalDisasters : Fragment() {

    private val binding by lazy {
        FragmentNaturalDisastersBinding.inflate(layoutInflater)
    }

    private val mainComponent by lazy {
        (requireActivity().application as App).provideMainComponent()
    }

    private val component by lazy{
        DaggerActivityComponent.factory().create(mainComponent)
    }

    @Inject
    lateinit var fabric: ViewModelsFabric
    private val vm by lazy {
        ViewModelProvider(this, fabric)[CameraViewModel::class.java]
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectNaturalDisasters(this)
    }


}