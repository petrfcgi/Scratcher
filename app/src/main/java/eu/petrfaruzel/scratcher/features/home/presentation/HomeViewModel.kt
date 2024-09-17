package eu.petrfaruzel.scratcher.features.home.presentation

import androidx.lifecycle.viewModelScope
import eu.petrfaruzel.scratcher.core.presentation.compose.ComposeNotifiableViewModel
import eu.petrfaruzel.scratcher.features.shared.domain.CardRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val cardRepository: CardRepository,
) : ComposeNotifiableViewModel<HomeVO, HomeEvent, HomeNotifyEvent>() {

    init {
        viewModelScope.launch {
            observeSettings()
        }
    }

    private suspend fun observeSettings() {
        combine(
            cardRepository.getOwnedScratchCode(),
            cardRepository.isCardActivated()
        ) { scratchCode, isCardActivated ->
            HomeVO(
                isScratched = !scratchCode.isNullOrBlank(),
                isActivated = isCardActivated
            )
        }.onEach { state ->
            updateState(state)
        }.launchIn(viewModelScope)
    }

    override suspend fun fetchData() {}

    override fun handleEvent(event: HomeEvent, state: HomeVO) {
        when (event) {
            HomeEvent.ActivationScreenClicked -> sendNotifyEvent(HomeNotifyEvent.GoToActivationScreen)
            HomeEvent.ScratchScreenClicked -> sendNotifyEvent(HomeNotifyEvent.GoToScratchScreen)
            HomeEvent.ResetCard -> {
                viewModelScope.launch {
                    cardRepository.setCardActivated(null)
                    cardRepository.setOwnedScratchCode(null)
                }
            }
        }
    }

}