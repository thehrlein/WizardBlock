package com.tobiashehrlein.tobiswizardblock.interactor.usecase

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

abstract class BaseFlowUseCase<in P, out R> {

    /**
     * Override this to set the code to be executed.
     */
    protected abstract fun execute(parameters: P): Flow<AppResult<R>>

    /**
     * Executes the use case.
     *
     * @param parameters the input parameters to run the use case with
     */
    operator fun invoke(parameters: P): Flow<AppResult<R>> {
        return try {
            execute(parameters)
        } catch (e: Exception) {
            flowOf(AppResult.Error(cause = e).also {
                Timber.e(it.toString())
            })
        }
    }
}

 operator fun <R> BaseFlowUseCase<Unit, R>.invoke(): Flow<AppResult<R>> {
    return this(Unit)
}
