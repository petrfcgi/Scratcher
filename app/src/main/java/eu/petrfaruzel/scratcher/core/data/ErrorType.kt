package eu.petrfaruzel.scratcher.core.data

interface ErrorType

sealed class DefaultDataErrorType : ErrorType {
    data object ResponseParsingError : DefaultDataErrorType()
    data object ApiResponseError : DefaultDataErrorType()
    data object NoNetworkConnection : DefaultDataErrorType()
}