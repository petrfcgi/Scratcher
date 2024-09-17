package eu.petrfaruzel.scratcher.features.scratch.presentation

import androidx.lifecycle.viewModelScope
import eu.petrfaruzel.scratcher.core.compose.ComposeViewModel
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState
import eu.petrfaruzel.scratcher.features.shared.domain.CardRepository
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScratchViewModel(
    private val cardRepository: CardRepository,
) : ComposeViewModel<ScratchVO, ScratchEvent>() {

    init {
        viewModelScope.launch {
            observeData()
        }
    }

    private suspend fun observeData() {
        cardRepository.getOwnedScratchCode().stateIn(
            scope = viewModelScope,
        ).collect { code ->
            updateState(
                ScratchVO(
                    scratchCodeState = UIState.Loaded(code)
                )
            )
        }
    }

    override suspend fun fetchData() {}

    override fun handleEvent(
        event: ScratchEvent,
        state: ScratchVO,
    ) {
        when (event) {
            ScratchEvent.InitScratching -> {
                viewModelScope.launch {
                    updateState(state.copy(scratchCodeState = UIState.Loading()))
                    val result = cardRepository.getScratchCode()
                    when (result) {
                        is DataRequestResult.Failure -> updateState(state.copy(scratchCodeState = UIState.Error()))
                        is DataRequestResult.Success -> {/*  might show confetti via notify */
                        }
                    }
                }
            }
        }
    }
}