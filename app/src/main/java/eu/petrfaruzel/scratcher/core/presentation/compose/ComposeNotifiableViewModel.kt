package eu.petrfaruzel.scratcher.core.presentation.compose

import androidx.lifecycle.viewModelScope
import eu.petrfaruzel.scratcher.core.compose.ComposeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class ComposeNotifiableViewModel<T, E : UIEvent, N : NotifyEvent>(defaultState: UIState<T> = UIState.Loading()) :
    ComposeViewModel<T, E>(defaultState) {

    private val _notifyEvent = MutableSharedFlow<N>()
    val notifyEvent: SharedFlow<N> = _notifyEvent.asSharedFlow()

    protected fun sendNotifyEvent(event: N) {
        viewModelScope.launch {
            _notifyEvent.emit(event)
        }
    }

}