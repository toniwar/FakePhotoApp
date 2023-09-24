package toniwar.projects.fakephotoapp.di.main_component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import toniwar.projects.fakephotoapp.App
import toniwar.projects.fakephotoapp.data.database.ClipArtsDAO
import toniwar.projects.fakephotoapp.data.network.RetrofitBuilder
import javax.inject.Scope

@MainComponent.Companion.MainScope
@Component(modules = [DataModule::class])
interface MainComponent {
    fun inject(app: App)
    fun provideContext(): Context

    fun provideDAO(): ClipArtsDAO

    fun provideRetrofit(): RetrofitBuilder

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
