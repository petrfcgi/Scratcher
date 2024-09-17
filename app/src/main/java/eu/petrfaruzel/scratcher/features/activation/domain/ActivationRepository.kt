package eu.petrfaruzel.scratcher.features.activation.domain

import eu.petrfaruzel.scratcher.config.Configuration
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.core.domain.handleApiCall
import eu.petrfaruzel.scratcher.features.activation.data.ActivationApi
import eu.petrfaruzel.scratcher.features.activation.data.VersionDTO
import eu.petrfaruzel.scratcher.features.shared.data.CardDS


class ActivationRepository(
    private val activationApi: ActivationApi,
    private val cardDS: CardDS,
) {
    suspend fun activateCard(code: String): DataRequestResult<VersionDTO> {
        val result = handleApiCall {
            activationApi.getVersion(code)
        }
        when (result) {
            is DataRequestResult.Success -> {
                if ((result.value.android.toIntOrNull() ?: 0) >= Configuration.MIN_SUPPORTED_API_VERSION) {
                    cardDS.setIsActivated(true)
                    return result
                } else {
                    return DataRequestResult.Failure()
                }
            }

            is DataRequestResult.Failure -> {
                return result
            }
        }
    }
}