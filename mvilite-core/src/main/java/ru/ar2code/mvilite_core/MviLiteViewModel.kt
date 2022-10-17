package ru.ar2code.mvilite_core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Simple MVI ViewModel that provides ui state and side effects as flows.
 * UI State can be atomically updated by some of [updateState] methods.
 * New subscribers will receive current state.
 */
abstract class MviLiteViewModel<S>(
    protected val initialStateFactory: MviLiteInitialStateFactory<S>
) : ViewModel() {

    private val viewStateMutable =
        MutableStateFlow(initialStateFactory.getInitialState()).also { stateFlow ->
            viewModelScope.launch {
                initialStateFactory.loadState()?.let { loadedState ->
                    stateFlow.emit(loadedState)
                }
            }
        }

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
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     */
    protected suspend fun updateState(reducerFunction: suspend (S) -> S?) {
        viewStateMutable.updateIfNotNull(reducerFunction)
    }

    /**
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     *
     * @return previous state
     */
    protected suspend fun updateStateAndGetPrevious(reducerFunction: suspend (S) -> S?): S {
        return viewStateMutable.updateIfNotNullAndGetPrevious(reducerFunction)
    }

    /**
     * Update UI state atomically.
     * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated, so
     * you should not put any calculation operations inside reducer, if recalculating is not required by your logic.
     *
     * @return new state
     */
    protected suspend fun updateStateAndGetUpdated(reducerFunction: suspend (S) -> S?): S? {
        return viewStateMutable.updateIfNotNullAndGetUpdated(reducerFunction)
    }
}