package eu.petrfaruzel.scratcher.di

import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration
): KoinApplication = startKoin {
    androidLogger()
    appDeclaration()
    modules(
        modules = getModules()
    )
}

private fun getModules(): List<Module> {
    return listOf(
        presentationModules,
        domainModules,
        dataModules
    )
}