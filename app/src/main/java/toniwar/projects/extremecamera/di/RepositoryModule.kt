package toniwar.projects.extremecamera.di

import dagger.Binds
import dagger.Module
import toniwar.projects.extremecamera.data.CameraRepositoryImpl
import toniwar.projects.extremecamera.domain.CameraRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindCameraRepository(impl: CameraRepositoryImpl): CameraRepository
}