package eu.petrfaruzel.scratcher.features.home.presentation

import eu.petrfaruzel.scratcher.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.petrfaruzel.scratcher.core.presentation.compose.ViewModelStateWrapper
import eu.petrfaruzel.scratcher.features.shared.presentation.routes.ScreenRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel(),
) {

    LaunchedEffect(Unit) {
        homeViewModel.notifyEvent.collect { event ->
            homeScreenNotifyEventHandler(navController, event)
        }
    }

    ViewModelStateWrapper(homeViewModel) { state ->
        HomeScreenInternal(state) { event ->
            homeViewModel.handleEvent(event)
        }
    }
}

@Composable
private fun HomeScreenInternal(state: HomeVO, onEvent: (HomeEvent) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            stringResource(R.string.card_state),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            "${stringResource(R.string.is_scratched)}: " +
                    if (state.isScratched) stringResource(R.string.yes) else stringResource(R.string.no)
        )
        Text(
            "${stringResource(R.string.is_activated)}: " +
                    if (state.isActivated) stringResource(R.string.yes) else stringResource(R.string.no)
        )

        Spacer(modifier = Modifier.weight(2f))

        Button(onClick = {
            onEvent(HomeEvent.ScratchScreenClicked)
        }) {
            Text(stringResource(R.string.go_to_scratch_screen))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onEvent(HomeEvent.ActivationScreenClicked)
        }) {
            Text(stringResource(R.string.go_to_activation_screen))
        }

        if (state.isScratched && state.isActivated) {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                onEvent(HomeEvent.ResetCard)
            }) {
                Text(stringResource(R.string.reset))
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

private fun homeScreenNotifyEventHandler(
    navController: NavController,
    event: HomeNotifyEvent,
) {
    when (event) {
        HomeNotifyEvent.GoToScratchScreen -> navController.navigate(ScreenRoute.ScratchScreen.route)
        HomeNotifyEvent.GoToActivationScreen -> navController.navigate(ScreenRoute.ActivationScreen.route)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenInternal(
        state = HomeVO(isScratched = true, isActivated = false)
    ) { }
}