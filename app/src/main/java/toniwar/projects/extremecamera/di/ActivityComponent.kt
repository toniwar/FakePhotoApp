package toniwar.projects.extremecamera.di

import dagger.Component
import toniwar.projects.extremecamera.presentation.MainActivity
import toniwar.projects.extremecamera.presentation.fragments.CameraX
import toniwar.projects.extremecamera.presentation.fragments.Editor
import javax.inject.Scope

@ActivityComponent.Companion.ActivityComponentScope
@Component(
    dependencies = [MainComponent::class],
    modules = [ViewModelsModule::class, RepositoryModule::class]
)
interface ActivityComponent {

    fun injectActivity(activity: MainActivity)

    fun injectCameraX(fragment: CameraX)

    fun injectEditor(fragment: Editor)

    @Component.Factory
    interface ActivityComponentFactory{

        fun create(mainComponent: MainComponent): ActivityComponent

    }

    companion object{

        @MustBeDocumented
        @Scope
        @Retention(AnnotationRetention.RUNTIME)
        annotation class ActivityComponentScope

    }

}