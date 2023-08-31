package toniwar.projects.extreamcamera.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import toniwar.projects.extreamcamera.presentation.view_models.CameraViewModel
import toniwar.projects.extreamcamera.presentation.view_models.vm_fabric.ViewModelsFabric


@Module
interface ViewModelsModule {

    @IntoMap
    @ViewModelsFabric.Companion.ViewModelKey(CameraViewModel::class)
    @Binds
    fun bindCameraViewModel(impl: CameraViewModel): ViewModel

}