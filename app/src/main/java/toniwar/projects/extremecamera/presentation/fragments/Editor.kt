package toniwar.projects.extremecamera.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import toniwar.projects.extremecamera.App
import toniwar.projects.extremecamera.databinding.FragmentEditorBinding
import toniwar.projects.extremecamera.di.DaggerActivityComponent
import toniwar.projects.extremecamera.presentation.SamplesAdapter
import toniwar.projects.extremecamera.presentation.view_models.EditorViewModel
import toniwar.projects.extremecamera.presentation.view_models.vm_fabric.ViewModelsFabric
import javax.inject.Inject


class Editor : Fragment() {
    private var path: String? = null

    private val binding by lazy {
        FragmentEditorBinding.inflate(layoutInflater)
    }

    private val mainComponent by lazy {
        (requireActivity().application as App).provideMainComponent()
    }

    private val component by lazy {
        DaggerActivityComponent.factory().create(mainComponent)
    }

    private val samplesAdapter by lazy {
        SamplesAdapter()
    }


    @Inject
    lateinit var fabric: ViewModelsFabric
    private val vm by lazy {
        ViewModelProvider(this, fabric)[EditorViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            path = it.getString(URL_KEY)
        }
        return binding.root
    }

    @SuppressLint("SdCardPath")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectEditor(this)

        binding.apply {
            photo.setImage(ImageSource.uri(path!!))
            vm.rotateImageView(photo)

        }
        Log.d("ImagePath", path!!)
        binding.elementsRv.adapter = samplesAdapter
        samplesAdapter.itemListener = {
            vm.inlineImage(binding.containerLayout, it.img)
        }


        binding.addElementButton.apply {
            setOnClickListener {
                vm.showMenu(binding.guideline)
                vm.changeMenuButtonIcon {
                    setImageResource(it)
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
                            getSamplesList()
                        }
                        delay(3000)
                    }
                }.collect()
            }
        }

    }

    private suspend fun getSamplesList(){
        vm.samplesList.collectLatest{samples->
            samples?.let {
                samplesAdapter.loadSamples(it.samples)

            }

        }

    }


    companion object {
        const val URL_KEY = "Url"
        fun newInstance(arg: String? = null) = Editor().apply {
            arguments = Bundle().apply {
                putString(URL_KEY, arg)
            }
        }

    }

}

