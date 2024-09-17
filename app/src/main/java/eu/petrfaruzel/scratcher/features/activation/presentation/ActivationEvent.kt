package eu.petrfaruzel.scratcher.features.activation.presentation

import eu.petrfaruzel.scratcher.core.presentation.compose.UIEvent

sealed class ActivationEvent : UIEvent {
    data object ActivateCard : ActivationEvent()
}