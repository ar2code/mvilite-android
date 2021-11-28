package ru.ar2code.mvilite_core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Simple MVI ViewModel that provides ui state and side effects as flows.
 * UI State can be atomically updated by some of [updateState] methods.
 * New subscribers will receive current state.
 *
 * Side effects can be emitted with [emitSideEffect].
 * New subscribers will not receive previous effect. Only active subscribers can receive side effects.
 */
abstract class MviLiteViewModel<S, E>(
    initialStateFactory: MviLiteInitialStateFactory<S>
) : ViewModel() {

    private val viewStateMutable = MutableStateFlow(initialStateFactory.getState())
    private val sideEffectsMutable =
        MutableSharedFlow<E>(0, 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    /**
     * UI State flow
     */
    val uiState = viewStateMutable.asStateFlow()

    /**
     * Side effects flow
     */
    val uiSideEffects = sideEffectsMutable.asSharedFlow()

    /**
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     */
    protected fun updateState(reducerFunction: (S) -> S?) {
        viewStateMutable.updateIfNotNull(reducerFunction)
    }

    /**
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     *
     * @return previous state
     */
    protected fun updateStateAndGetPrevious(reducerFunction: (S) -> S?): S {
        return viewStateMutable.updateIfNotNullAndGetPrevious(reducerFunction)
    }

    /**
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     *
     * @return new state
     */
    protected fun updateStateAndGetUpdated(reducerFunction: (S) -> S?): S? {
        return viewStateMutable.updateIfNotNullAndGetUpdated(reducerFunction)
    }

    /**
     * Send side effect
     */
    protected fun emitSideEffect(effect: E) {
        sideEffectsMutable.tryEmit(effect)
    }

    /**
     * Helper method that allows you to handle [message] inside some logic that returns a flow [messageInteractor].
     * Result [R] from that flow will be passed as parameter to [reducer] for making a new state.
     *
     * After state will be reduced you can emit some side effect with [sideEffectAfter].
     */
    protected fun <M, R> reduceWithInteractor(
        message: M,
        messageInteractor: ((M) -> Flow<R>),
        reducer: MviLiteStateReducer<M, S, R>,
        sideEffectAfter: MviLiteSideEffectProducer<M, S, E, R>? = null
    ) {
        viewModelScope.launch {
            messageInteractor.invoke(message).collect { result ->
                reduce(message, result, reducer, sideEffectAfter)
            }
        }
    }

    /**
     * Helper method that allows you to handle [message] inside [reducer].
     * In that case message will be passed as parameter to reducer.
     *
     * If you need to do some suspending logic before reducing, consider method [reduceWithInteractor].
     *
     * After state will be reduced you can emit some side effect with [sideEffectAfter].
     */
    protected fun <M, R> reduce(
        message: M,
        reducer: MviLiteStateReducer<M, S, R>,
        sideEffectAfter: MviLiteSideEffectProducer<M, S, E, R>? = null
    ) {
        reduce(message, null, reducer, sideEffectAfter)
    }

    /**
     * Reduce dispatched message and result from interactor to a new state.
     */
    internal fun <M, R> reduce(
        message: M,
        result: R?,
        reducer: MviLiteStateReducer<M, S, R>,
        sideEffectAfter: MviLiteSideEffectProducer<M, S, E, R>? = null
    ) {
        val updatedState = updateStateAndGetUpdated { newState ->
            reducer.reduce(message, newState, result)
        }
        sideEffectAfter?.getEffectAfterStateUpdating(
            message,
            updatedState ?: uiState.value,
            updatedState != null,
            result
        )?.let { effect ->
            emitSideEffect(effect)
        }
    }
}