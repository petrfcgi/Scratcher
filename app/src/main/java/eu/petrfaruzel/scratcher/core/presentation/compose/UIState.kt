package eu.petrfaruzel.scratcher.core.presentation.compose

import eu.petrfaruzel.scratcher.R
import androidx.annotation.StringRes
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.core.data.DefaultDataErrorType


sealed class UIState<T> {
    data class Loaded<T>(val value: T) : UIState<T>()
    data class Error<T>(@StringRes val errorRes: Int = R.string.error_generic) : UIState<T>()
    data class Loading<T>(val value: T? = null) : UIState<T>()

    fun getOrNull(): T? {
        return when (this) {
            is Loaded -> value
            is Error -> null
            is Loading -> value
        }
    }

    companion object {

        fun <T> errorMessageResFromResult(result: DataRequestResult.Failure<T>): Int {
            val error = result.errorType
            if (error is DefaultDataErrorType) {
                return when (error) {
                    DefaultDataErrorType.NoNetworkConnection -> R.string.error_no_network
                    DefaultDataErrorType.ResponseParsingError -> R.string.error_response_parsing
                    DefaultDataErrorType.ApiResponseError -> R.string.error_api_response
                }
            }
            return R.string.error_generic
        }
    }

}