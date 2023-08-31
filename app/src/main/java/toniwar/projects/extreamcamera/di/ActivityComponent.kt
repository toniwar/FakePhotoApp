package toniwar.projects.extreamcamera.di

import dagger.Component
import toniwar.projects.extreamcamera.presentation.MainActivity
import toniwar.projects.extreamcamera.presentation.fragments.NaturalDisasters
import javax.inject.Scope

@ActivityComponent.Companion.ActivityComponentScope
@Component(
    dependencies = [MainComponent::class],
    modules = [ViewModelsModule::class]
)
interface ActivityComponent {

    fun injectActivity(activity: MainActivity)

    fun injectNaturalDisasters(fragment: NaturalDisasters)

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