package toniwar.projects.extremecamera

import android.app.Application
import toniwar.projects.extremecamera.di.DaggerMainComponent
import toniwar.projects.extremecamera.di.MainComponent

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