package eu.petrfaruzel.scratcher.features.shared.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.petrfaruzel.scratcher.features.activation.presentation.ActivationScreen
import eu.petrfaruzel.scratcher.features.home.presentation.HomeScreen
import eu.petrfaruzel.scratcher.features.scratch.presentation.ScratchScreen
import eu.petrfaruzel.scratcher.features.shared.presentation.routes.ScreenRoute
import eu.petrfaruzel.scratcher.ui.theme.ScratcherTheme


@Preview(showBackground = true)
@Composable
fun RootScreen() {
    val rootNavController = rememberNavController()

    ScratcherTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = rootNavController,
                    startDestination = ScreenRoute.MainScreen.route
                ) {
                    composable(route = ScreenRoute.MainScreen.route) {
                        HomeScreen(rootNavController)
                    }
                    composable(route = ScreenRoute.ScratchScreen.route) {
                        ScratchScreen(rootNavController)
                    }
                    composable(route = ScreenRoute.ActivationScreen.route) {
                        ActivationScreen(rootNavController)
                    }
                }
            }
        }
    }
}