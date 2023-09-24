package toniwar.projects.fakephotoapp

import android.app.Application
import toniwar.projects.fakephotoapp.di.main_component.DaggerMainComponent
import toniwar.projects.fakephotoapp.di.main_component.MainComponent


class App: Application() {

    private val mainComponent by lazy { DaggerMainComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        mainComponent.inject(this)
    }

    fun provideMainComponent(): MainComponent {
        return mainComponent
    }
}
