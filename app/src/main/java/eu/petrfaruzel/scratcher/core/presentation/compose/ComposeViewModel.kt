package eu.petrfaruzel.scratcher.core.compose

import androidx.lifecycle.viewModelScope
import eu.petrfaruzel.scratcher.core.presentation.bases.BaseViewModel
import eu.petrfaruzel.scratcher.core.presentation.compose.UIEvent
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class ComposeViewModel<T, E : UIEvent>(defaultState: UIState<T> = UIState.Loading()) : BaseViewModel<T>(defaultState) {

    open val viewState: StateFlow<UIState<T>> = _viewState.asStateFlow()

    fun handleEvent(event: E) {
        viewModelScope.launch {
            val state = _viewState.value.getOrNull() ?: return@launch
            handleEvent(event = event, state = state)
        }
    }

    protected abstract fun handleEvent(event: E, state: T)

    protected abstract suspend fun fetchData()

    protected fun updateState(newState: UIState<T>): UIState<T> {
        _viewState.update { newState }
        return newState
    }

    protected open fun updateState(newState: T): T {
        _viewState.update { UIState.Loaded(newState) }
        return newState
    }

    open fun reloadData() {
        _viewState.update {
            UIState.Loading(
                value = _viewState.value.getOrNull()
            )
        }
        viewModelScope.launch {
            fetchData()
        }
    }
}