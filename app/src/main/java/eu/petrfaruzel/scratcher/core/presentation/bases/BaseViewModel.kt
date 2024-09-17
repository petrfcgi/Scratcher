package eu.petrfaruzel.scratcher.core.presentation.bases

import androidx.lifecycle.ViewModel
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<T>(defaultState: UIState<T> = UIState.Loading()) : ViewModel(), KoinComponent {

    @Suppress("PropertyName")
    protected val _viewState = MutableStateFlow(defaultState)
}