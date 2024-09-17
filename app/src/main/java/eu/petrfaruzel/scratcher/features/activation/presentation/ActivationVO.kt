package eu.petrfaruzel.scratcher.features.activation.presentation

import androidx.annotation.StringRes
import eu.petrfaruzel.scratcher.core.presentation.compose.UIState

data class ActivationVO(
    val cardCode: String?,
    val isActivated: UIState<Boolean>,
    val errorModal: ActivationErrorModal? = null
)

data class ActivationErrorModal(
    @StringRes val errorMessageRes: Int,
    val onDismissed: () -> Unit
)