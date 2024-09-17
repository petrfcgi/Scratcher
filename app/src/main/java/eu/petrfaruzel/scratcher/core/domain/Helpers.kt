package eu.petrfaruzel.scratcher.core.domain

import de.jensklingenberg.ktorfit.Response
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.core.data.DefaultDataErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend inline fun <reified T> handleApiCall(
    noinline apiCall: suspend () -> Response<T>,
): DataRequestResult<T> {

    return withContext(Dispatchers.IO) {
        val apiResult: Response<T>?
        try {
            apiResult = apiCall()
        } catch (e: Exception) {
            Timber.w("Error occurred during network call for method ${apiCall.javaClass.simpleName}")
            return@withContext DataRequestResult.Failure(
                errorType = DefaultDataErrorType.NoNetworkConnection,
                exception = e
            )
        }

        if (apiResult.isSuccessful) {
            try {
                return@withContext DataRequestResult.Success(value = apiResult.body()!!)
            } catch (e: Exception) {
                return@withContext DataRequestResult.Failure(
                    errorType = DefaultDataErrorType.ResponseParsingError,
                    exception = e
                )
            }
        } else {
            Timber.w(
                "Error occurred during network call for ${apiResult.javaClass.simpleName}\ncode: ${apiResult.status}\nException of: ${apiResult.message}"
            )
            return@withContext DataRequestResult.Failure(
                errorType = DefaultDataErrorType.ApiResponseError,
                rawErrorMessage = apiResult.message
            )
        }
    }
}