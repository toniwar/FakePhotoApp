package toniwar.projects.extreamcamera.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import toniwar.projects.extreamcamera.App
import javax.inject.Scope

@MainComponent.Companion.MainScope
@Component(modules = [])
interface MainComponent {
    fun inject(app: App)
    fun provideContext(): Context

    @Component.Factory
    interface AppComponentFactory{
        fun create(
            @BindsInstance
            context: Context
        ): MainComponent

    }

    companion object{
        @MustBeDocumented
        @Scope
        @Retention(AnnotationRetention.RUNTIME)
        annotation class MainScope
    }
}