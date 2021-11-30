package ru.ar2code.mvilite_core

/**
 * Simple reducer that takes intent with current state and reduces that to a new state.
 */
interface MviLiteReducer<I, S> {

    /**
     * Reduce method can be invoked multiple time due to concurrency,
     * so you should not run any long operations inside reduce method.
     * Finish all calculations before and pass a result as intent parameter.
     */
    fun reduce(intent: I, currentState: S): S?
}