package toniwar.projects.extremecamera.di

import dagger.Binds
import dagger.Module
import toniwar.projects.extremecamera.data.repositories.CameraRepositoryImpl
import toniwar.projects.extremecamera.data.repositories.DataRepositoryImpl
import toniwar.projects.extremecamera.domain.repositories.CameraRepository
import toniwar.projects.extremecamera.domain.repositories.DataRepository

@Module
interface RepositoryModule {

    @ActivityComponent.Companion.ActivityComponentScope
    @Binds
    fun bindCameraRepository(impl: CameraRepositoryImpl): CameraRepository

    @Binds
    fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
}