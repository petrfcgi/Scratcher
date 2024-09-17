package eu.petrfaruzel.scratcher.core.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import eu.petrfaruzel.scratcher.config.Configuration

object KtorfitProvider {
    val ktorfit = Ktorfit.Builder()
        .baseUrl(Configuration.BASE_URL) // Replace with your API base URL
        .httpClient(KtorClientProvider.client)
        .converterFactories(ResponseConverterFactory())
        .build()
}