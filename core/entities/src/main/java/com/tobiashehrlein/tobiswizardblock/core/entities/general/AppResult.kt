package com.tobiashehrlein.tobiswizardblock.core.entities.general

sealed class AppResult<out T> {

    data class Success<out T>(val value: T) : AppResult<T>()

    data class Error(
        val reason: Reason = Reason.Unknown,
        val message: String = "No error message",
        val cause: Throwable? = null
    ) : AppResult<Nothing>() {

        sealed class Reason {

            object NoConnection : Reason()
            object Parsing : Reason()
            object Unauthorized : Reason()
            object Unknown : Reason()
            object NoBody : Reason()
        }
    }

    fun <R> map(transform: (T) -> R): AppResult<R> {
        return when (this) {
            is Success -> Success(
                transform(value)
            )
            is Error -> this
        }
    }
}

fun <T> T.toSuccessResult(): AppResult<T> {
    return AppResult.Success(this)
}
