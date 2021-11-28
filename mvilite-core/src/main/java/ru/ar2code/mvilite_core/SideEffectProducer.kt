package ru.ar2code.mvilite_core

/**
 * Clean function just get side effect based on updated state.
 */
interface SideEffectProducer<S, E, P> {
    fun getEffect(state: S, parameter: P?): E?
}