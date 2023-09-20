package toniwar.projects.fakephotoapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import toniwar.projects.fakephotoapp.presentation.view_models.CameraViewModel
import toniwar.projects.fakephotoapp.presentation.view_models.EditorViewModel
import toniwar.projects.fakephotoapp.presentation.view_models.FinalResultViewModel
import toniwar.projects.fakephotoapp.presentation.view_models.vm_fabric.ViewModelsFabric


@Module
interface ViewModelsModule {

    @IntoMap
    @ViewModelsFabric.Companion.ViewModelKey(CameraViewModel::class)
    @Binds
    fun bindCameraViewModel(impl: CameraViewModel): ViewModel

    @IntoMap
    @ViewModelsFabric.Companion.ViewModelKey(EditorViewModel::class)
    @Binds
    fun bindEditorViewModel(impl: EditorViewModel): ViewModel

    @IntoMap
    @ViewModelsFabric.Companion.ViewModelKey(FinalResultViewModel::class)
    @Binds
    fun bindFinalResultViewModel(impl: FinalResultViewModel): ViewModel

}
