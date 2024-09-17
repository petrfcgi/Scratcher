package eu.petrfaruzel.scratcher.core.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.petrfaruzel.scratcher.core.compose.ComposeViewModel
import eu.petrfaruzel.scratcher.core.presentation.compose.screens.ErrorScreenWithRetry
import eu.petrfaruzel.scratcher.core.compose.screens.LoadingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun <T, E : UIEvent> ViewModelStateWrapper(
    viewModel: ComposeViewModel<T, E>,
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = true,
    loading: @Composable () -> Unit = {
        LoadingScreen(fillMaxSize = true)
    },
    error: @Composable (errorResId: Int) -> Unit = {
        ErrorScreenWithRetry(
            errorResId = it,
            onRetry = {
                viewModel.reloadData()
            })
    },
    loaded: @Composable (state: T) -> Unit,
) {
    val vmState = viewModel.viewState.collectAsStateWithLifecycle().value

    if (fillMaxSize) {
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
    }
    Box(modifier = modifier) {
        when (vmState) {
            is UIState.Loading -> loading()
            is UIState.Loaded -> loaded(vmState.value)
            is UIState.Error -> error(vmState.errorRes)
        }
    }
}