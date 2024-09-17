package eu.petrfaruzel.scratcher.features.activation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.petrfaruzel.scratcher.R
import eu.petrfaruzel.scratcher.core.compose.screens.LoadingScreen
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState
import eu.petrfaruzel.scratcher.core.presentation.compose.ViewModelStateWrapper
import eu.petrfaruzel.scratcher.features.shared.presentation.routes.ScreenRoute
import org.koin.androidx.compose.koinViewModel


@Composable
fun ActivationScreen(
    navController: NavController,
    activationViewModel: ActivationViewModel = koinViewModel(),
) {

    ViewModelStateWrapper(activationViewModel) { state ->
        ActivationScreenInternal(navController, state) { event ->
            activationViewModel.handleEvent(event)
        }
    }
}

@Composable
private fun ActivationScreenInternal(
    navController: NavController,
    state: ActivationVO,
    onEvent: (ActivationEvent) -> Unit,
) {

    val isActivationAvailable = state.isActivated.getOrNull() == false && !state.cardCode.isNullOrBlank()

    DialogHandler(state.errorModal)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            if (state.isActivated is UIState.Loading) {
                Text(
                    stringResource(R.string.card_activation_in_progress),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LoadingScreen(fillMaxSize = false)
                Spacer(Modifier.padding(bottom = 64.dp))
            }
        }
        Button(
            enabled = isActivationAvailable,
            onClick = {
                onEvent(ActivationEvent.ActivateCard)
            }) {
            Text(text = stringResource(R.string.activate_card))
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                navController.popBackStack(ScreenRoute.MainScreen.route, inclusive = false)
            }) {
            Text(text = stringResource(R.string.go_back))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DialogHandler(modal: ActivationErrorModal?) {
    if (modal != null) {
        AlertDialog(
            text = {
                Text(text = stringResource(modal.errorMessageRes))
            },
            confirmButton = {
                Button(onClick = { modal.onDismissed() }) {
                    Text(stringResource(R.string.understand))
                }
            },
            onDismissRequest = { modal.onDismissed() }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ActivationScreenInternal(
        navController = rememberNavController(),
        state = ActivationVO(cardCode = "1234-56789", isActivated = UIState.Loading())
    ) { }
}