package eu.petrfaruzel.scratcher.features.scratch.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun ScratchScreen(
    navController: NavController,
    scratchViewModel: ScratchViewModel = koinViewModel(),
) {

    ViewModelStateWrapper(scratchViewModel) { state ->
        ScratchScreenInternal(navController, state) { event ->
            scratchViewModel.handleEvent(event)
        }
    }
}

@Composable
private fun ScratchScreenInternal(
    navController: NavController,
    state: ScratchVO,
    onEvent: (ScratchEvent) -> Unit,
) {

    val isScratched = state.scratchCodeState is UIState.Loaded
            && !state.scratchCodeState.value.isNullOrBlank()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.2f),
            colors = CardDefaults.cardColors(
                containerColor = if (isScratched) Color(150, 200, 166) else Color.Unspecified
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                when (val scratchState = state.scratchCodeState) {
                    is UIState.Error -> Text(text = stringResource(scratchState.errorRes))
                    is UIState.Loaded -> if (isScratched) {
                        Text(text = scratchState.value)
                    } else {
                        Text(text = stringResource(R.string.scratch_with_button_below))
                    }

                    is UIState.Loading -> LoadingScreen(fillMaxSize = false)
                }
            }
        }


        Spacer(modifier = Modifier.weight(2f))

        Button(
            enabled = !isScratched,
            onClick = {
                onEvent(ScratchEvent.InitScratching)
            }) {
            Text(text = stringResource(R.string.scratch_code))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack(ScreenRoute.MainScreen.route, inclusive = false)
        }) {
            Text(text = stringResource(R.string.go_back))
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ScratchScreenInternal(
        navController = rememberNavController(),
        state = ScratchVO(scratchCodeState = UIState.Loaded(null))
    ) { }
}