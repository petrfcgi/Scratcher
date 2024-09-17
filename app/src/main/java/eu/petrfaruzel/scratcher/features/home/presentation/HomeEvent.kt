package eu.petrfaruzel.scratcher.features.home.presentation

import eu.petrfaruzel.scratcher.core.presentation.compose.UIEvent

sealed class HomeEvent : UIEvent {

    data object ScratchScreenClicked : HomeEvent()
    data object ActivationScreenClicked : HomeEvent()
    data object ResetCard : HomeEvent()

}