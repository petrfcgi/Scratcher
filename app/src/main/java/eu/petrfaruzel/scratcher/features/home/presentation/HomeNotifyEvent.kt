package eu.petrfaruzel.scratcher.features.home.presentation

import eu.petrfaruzel.scratcher.core.presentation.compose.NotifyEvent

sealed class HomeNotifyEvent : NotifyEvent {

    data object GoToScratchScreen : HomeNotifyEvent()
    data object GoToActivationScreen : HomeNotifyEvent()

}