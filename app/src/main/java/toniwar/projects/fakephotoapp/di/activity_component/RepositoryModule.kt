package toniwar.projects.fakephotoapp.di.activity_component

import dagger.Binds
import dagger.Module
import toniwar.projects.fakephotoapp.data.repositories.CameraRepositoryImpl
import toniwar.projects.fakephotoapp.data.repositories.DataRepositoryImpl
import toniwar.projects.fakephotoapp.domain.repositories.CameraRepository
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

@Module
interface RepositoryModule {

    @ActivityComponent.Companion.ActivityComponentScope
    @Binds
    fun bindCameraRepository(impl: CameraRepositoryImpl): CameraRepository

    @Binds
    fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
}
