package eu.petrfaruzel.scratcher.core.data

import timber.log.Timber

sealed class DataRequestResult<T> {
    data class Success<T>(val value: T) : DataRequestResult<T>()

    data class Failure<T>(
        val errorType: ErrorType? = null,
        val value: T? = null,
        val rawErrorMessage: String? = null,
        val exception: Exception? = null
    ) : DataRequestResult<T>() {
        init {
            if (exception == null)
                Timber.w("Fail occurred with type of $errorType | $rawErrorMessage")
            else
                Timber.e("Fail occurred with type of $errorType | $rawErrorMessage\n${exception}")
        }

        fun <T> asRequiredType(): Failure<T> {
            return Failure(
                errorType = errorType,
                rawErrorMessage = rawErrorMessage,
                exception = exception
            )
        }
    }

}