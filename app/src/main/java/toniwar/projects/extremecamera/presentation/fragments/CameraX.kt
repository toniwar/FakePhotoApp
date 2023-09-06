package toniwar.projects.extremecamera.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import toniwar.projects.extremecamera.App
import toniwar.projects.extremecamera.Permissions
import toniwar.projects.extremecamera.databinding.FragmentCameraXBinding
import toniwar.projects.extremecamera.di.DaggerActivityComponent
import toniwar.projects.extremecamera.domain.CameraRepository
import toniwar.projects.extremecamera.presentation.FragmentListener
import toniwar.projects.extremecamera.presentation.view_models.CameraViewModel
import toniwar.projects.extremecamera.presentation.view_models.vm_fabric.ViewModelsFabric
import javax.inject.Inject


class CameraX : Fragment() {

    private lateinit var listener: FragmentListener

    private val binding by lazy {
        FragmentCameraXBinding.inflate(layoutInflater)
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

    @Inject
    lateinit var cameraRepository: CameraRepository

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
        var permissionsGranted = true
        permissions.entries.forEach {
            if(it.key in Permissions.REQUIRED_PERMISSIONS && !it.value)
                permissionsGranted = false
        }

        if(!permissionsGranted){
            Toast.makeText(activity, "Deny!", Toast.LENGTH_SHORT).show()
        }
        else cameraRepository.startCamera(binding.photo, activity as LifecycleOwner)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectCameraX(this)
        if(!Permissions.hasPermissions(requireContext())) activityResultLauncher
            .launch(Permissions.REQUIRED_PERMISSIONS)
        else cameraRepository.startCamera(binding.photo, activity as LifecycleOwner)

        binding.takePhotoBtn.setOnClickListener {
            cameraRepository.takePhoto {
                listener.openFragment(FragmentListener.Companion.ActionFlag.EDITOR, it)
            }

        }

    }


}