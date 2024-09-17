package eu.petrfaruzel.scratcher

import android.app.Application
import eu.petrfaruzel.scratcher.di.initKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDebugger()
        initKoin { androidContext(this@App) }

        Timber.i("App started!")
    }

    private fun initDebugger() {
        Timber.plant(Timber.DebugTree())
    }

}