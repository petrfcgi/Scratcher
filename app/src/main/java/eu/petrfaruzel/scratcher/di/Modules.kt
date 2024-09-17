package eu.petrfaruzel.scratcher.di

import de.jensklingenberg.ktorfit.Ktorfit
import eu.petrfaruzel.scratcher.core.data.di.KtorClientProvider
import eu.petrfaruzel.scratcher.core.data.di.KtorfitProvider
import eu.petrfaruzel.scratcher.features.shared.domain.CardRepository
import eu.petrfaruzel.scratcher.features.home.presentation.HomeViewModel
import eu.petrfaruzel.scratcher.features.scratch.presentation.ScratchViewModel
import eu.petrfaruzel.scratcher.features.activation.data.ActivationApi
import eu.petrfaruzel.scratcher.features.activation.data.createActivationApi
import eu.petrfaruzel.scratcher.features.activation.domain.ActivationRepository
import eu.petrfaruzel.scratcher.features.activation.presentation.ActivationViewModel
import eu.petrfaruzel.scratcher.features.shared.data.CardDS
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModules = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ScratchViewModel)
    viewModelOf(::ActivationViewModel)
}

val domainModules = module {
    singleOf(::CardRepository)
    singleOf(::ActivationRepository)
}

val dataModules = module {
    // Ktor & ktorfit
    single { KtorClientProvider.client }
    single { KtorfitProvider.ktorfit }

    // Apis
    single<ActivationApi> { get<Ktorfit>().createActivationApi() }

    // Data Stores
    singleOf(::CardDS)
}