package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import timber.log.Timber

abstract class BaseUseCase<in P, out R> {

    /**
     * Override this to set the code to be executed.
     */
    protected abstract suspend fun execute(parameters: P): AppResult<R>

    /**
     * Executes the use case.
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): AppResult<R> {
        return try {
            execute(parameters)
        } catch (e: Exception) {
            AppResult.Error(cause = e).also {
                Timber.e(it.toString())
            }
        }
    }
}

suspend operator fun <R> BaseUseCase<Unit, R>.invoke(): AppResult<R> {
    return this(Unit)
}
