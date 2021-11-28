package ru.ar2code.mvilite_core

/**
 * Clean reduce function without any suspends, background operations.
 * Just make a new state from current state and some parameter that can be result from background operation, etc.
 *
 * Reduce function can be executed several time cause of unblocking concurrent modification algorithm,
 * that is why you should not do any long operations inside reducer. Use interactor for it.
 */
interface StateReducer<P, S> {
    fun reduce(currentState: S, param: P?): S
}