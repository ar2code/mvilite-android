package ru.ar2code.mvilite_core

/**
 * Clean function just get side effect based on state.
 */
interface MviLiteSideEffectProducer<M, S, E, R> {

    /**
     * Returns side effect depends on some logic and params.
     *
     * @param state - current state of a ViewModel
     * @param updatedState - if true reducer updated ViewModel's state
     * @param message - dispatched message to ViewModel
     * @param result - some extra parameter (generally it is a result from interactor)
     *
     * @return side effect that will be emitted if not null.
     */
    fun getEffectAfterStateUpdating(message: M, state: S, updatedState: Boolean, result: R?): E?
}