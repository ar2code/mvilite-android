package ru.ar2code.mvilite_core

/**
 * Clean reduce function without any suspends, background operations.
 * Just make a new state from current state and some parameter that can be result from background operation, etc.
 *
 * Reduce function can be executed several time cause of unblocking concurrent modification algorithm,
 * that is why you should not do any long operations inside reducer or emit side effects.
 */
interface MviLiteStateReducer<M, S, R> {
    /**
     * Reduce parameters (current state, dispatched message, result from interactor) to a new state.
     *
     * @param currentState - current state of a ViewModel
     * @param message - dispatched message to ViewModel
     * @param result - some extra parameter (generally it is a result from interactor)
     *
     * @return a new state. If null - viewModel's state will not be updated.
     */
    fun reduce(message: M, currentState: S, result: R?): S?
}