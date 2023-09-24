package toniwar.projects.fakephotoapp.presentation.view_models.vm_fabric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import toniwar.projects.fakephotoapp.di.activity_component.ActivityComponent
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@ActivityComponent.Companion.ActivityComponentScope
class ViewModelsFabric @Inject constructor(
    private val vMProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return vMProviders[modelClass]?.get() as T
    }


    companion object{
        @MapKey
        @Retention(AnnotationRetention.RUNTIME)
        annotation class ViewModelKey(val value: KClass<out ViewModel>)
    }
}