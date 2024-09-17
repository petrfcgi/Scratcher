package eu.petrfaruzel.scratcher.features.shared.presentation.routes

sealed class ScreenRoute(val route: String) {
    object MainScreen : ScreenRoute("main")
    object ScratchScreen : ScreenRoute("scratch")
    object ActivationScreen : ScreenRoute("activation")
}