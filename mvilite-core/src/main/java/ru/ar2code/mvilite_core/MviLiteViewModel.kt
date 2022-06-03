package ru.ar2code.mvilite_core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.reflect.KClass

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

    /**
     * UI State flow
     */
    val uiState = viewStateMutable.asStateFlow()

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
     * Reduce (intent,current state) to a new state
     * and update state atomically.
     *
     * @return new state
     */
    protected fun <I> updateWithReducerAndGetUpdated(intent: I, reducer: MviLiteReducer<I, S>): S? {
        return updateStateAndGetUpdated {
            reducer.reduce(intent, it)
        }
    }

    /**
     * Invoke specified [action] if [currentState] instance of [expectedState].
     */
    protected fun invokeForState(expectedState: KClass<*>, currentState: S?, action: () -> Unit) {
        if (expectedState.isInstance(currentState)) {
            action()
        }
    }
}