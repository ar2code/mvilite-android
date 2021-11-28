package ru.ar2code.mvilite_core

/**
 * Clean function just get side effect based on state.
 */
interface SideEffectProducer<S, E, P> {
    /**
     * @param state - current state
     * @param updatedState - if true reducer updated state
     * @param parameter - some extra parameter (can be dispatched message or result from interactor)
     */
    fun getEffectAfterStateUpdating(state: S, updatedState: Boolean, parameter: P?): E?
}