package ru.ar2code.mvilite_core

/**
 * Factory that creates initial state for [MviLiteViewModel]
 */
interface MviLiteInitialStateFactory<S> {

    /**
     * Get initial state
     */
    fun getState(): S
}