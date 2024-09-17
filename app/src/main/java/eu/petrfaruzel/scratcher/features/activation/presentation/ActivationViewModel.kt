package eu.petrfaruzel.scratcher.features.activation.presentation

import androidx.lifecycle.viewModelScope
import eu.petrfaruzel.scratcher.core.compose.ComposeViewModel
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState
import eu.petrfaruzel.scratcher.features.activation.domain.ActivationRepository
import eu.petrfaruzel.scratcher.features.shared.domain.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ActivationViewModel(
    private val activationRepository: ActivationRepository,
    private val cardRepository: CardRepository,
) : ComposeViewModel<ActivationVO, ActivationEvent>() {

    init {
        viewModelScope.launch {
            observeData()
        }
    }

    private suspend fun observeData() {
        combine(
            cardRepository.getOwnedScratchCode(),
            cardRepository.isCardActivated()
        ) { scratchCode, isCardActivated ->
            ActivationVO(
                cardCode = scratchCode,
                isActivated = UIState.Loaded(isCardActivated),
            )
        }.onEach { state ->
            updateState(state)
        }.launchIn(viewModelScope)
    }

    override suspend fun fetchData() {}

    override fun handleEvent(event: ActivationEvent, state: ActivationVO) {
        when (event) {
            ActivationEvent.ActivateCard -> {
                val cardCode = state.cardCode
                if (cardCode != null) {
                    updateState(state.copy(isActivated = UIState.Loading()))
                    val unstoppableScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

                    unstoppableScope.launch {
                        val result = activationRepository.activateCard(cardCode)
                        if (result is DataRequestResult.Failure) {
                            updateState(state.copy(
                                errorModal = ActivationErrorModal(
                                    errorMessageRes = UIState.errorMessageResFromResult(result),
                                    onDismissed = {
                                        val onDismissState = _viewState.value.getOrNull()
                                        if (onDismissState != null) updateState(state.copy(errorModal = null))
                                    }
                                ))
                            )
                        }
                    }
                }
            }
        }
    }
}