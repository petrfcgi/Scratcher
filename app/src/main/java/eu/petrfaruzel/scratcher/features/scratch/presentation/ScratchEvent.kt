package eu.petrfaruzel.scratcher.features.scratch.presentation

import eu.petrfaruzel.scratcher.core.presentation.compose.UIEvent

sealed class ScratchEvent : UIEvent {
    data object InitScratching : ScratchEvent()
}