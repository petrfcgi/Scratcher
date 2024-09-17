package eu.petrfaruzel.scratcher.features.scratch.presentation

import eu.petrfaruzel.scratcher.core.presentation.compose.UIState

data class ScratchVO(
    val scratchCodeState: UIState<String?>,
)