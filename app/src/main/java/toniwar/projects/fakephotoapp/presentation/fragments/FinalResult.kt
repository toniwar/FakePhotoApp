package toniwar.projects.fakephotoapp.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import toniwar.projects.fakephotoapp.App
import toniwar.projects.fakephotoapp.databinding.FragmentFinalResultBinding
import toniwar.projects.fakephotoapp.di.activity_component.DaggerActivityComponent
import toniwar.projects.fakephotoapp.presentation.listeners.FragmentListener
import toniwar.projects.fakephotoapp.presentation.view_models.FinalResultViewModel
import toniwar.projects.fakephotoapp.presentation.view_models.vm_fabric.ViewModelsFabric
import javax.inject.Inject


class FinalResult : Fragment() {

    private val binding by lazy {
        FragmentFinalResultBinding.inflate(layoutInflater)
    }

    private var path: String? = null

    private lateinit var listener: FragmentListener


    private val mainComponent by lazy {
        (requireActivity().application as App).provideMainComponent()
    }

    private val component by lazy {
        DaggerActivityComponent.factory().create(mainComponent)
    }

    @Inject
    lateinit var fabric: ViewModelsFabric

    private val vm by lazy{
        ViewModelProvider(this, fabric)[FinalResultViewModel::class.java]
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
        arguments?.let {

            path = it.getString(URI_KEY)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectFinalResult(this)
        vm.setImage(binding.resultImageView, Uri.parse(path))

        binding.apply {
            shareImageBtn.setOnClickListener {
                activity?.let {
                    vm.shareImage(it, Uri.parse(path))
                }

            }

            returnBtn.setOnClickListener {
                listener.openFragment(FragmentListener.Companion.ActionFlag.MENU, null)
            }
        }
    }

    companion object {
        private const val URI_KEY = "Uri"
        fun newInstance(arg: String? = null) = FinalResult().apply {
            arguments = Bundle().apply {
                putString(URI_KEY, arg)
            }
        }
    }
}

