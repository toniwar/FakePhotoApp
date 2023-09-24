package toniwar.projects.fakephotoapp.di.activity_component

import dagger.Component
import toniwar.projects.fakephotoapp.di.main_component.MainComponent
import toniwar.projects.fakephotoapp.presentation.MainActivity
import toniwar.projects.fakephotoapp.presentation.fragments.CameraX
import toniwar.projects.fakephotoapp.presentation.fragments.Editor
import toniwar.projects.fakephotoapp.presentation.fragments.FinalResult
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

    fun injectFinalResult(fragment: FinalResult)

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