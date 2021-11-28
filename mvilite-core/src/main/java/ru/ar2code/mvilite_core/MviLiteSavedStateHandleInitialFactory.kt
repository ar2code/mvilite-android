package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle

/**
 * Factory that has access to ViewModel's [SavedStateHandle].
 * You can restore view model and create appropriate initial state.
 */
abstract class MviLiteSavedStateHandleInitialFactory<S>(private val savedStateHandle: SavedStateHandle) :
    MviLiteInitialStateFactory<S> {

    /**
     * Get initial state from [savedStateHandle]
     */
    abstract fun getState(savedStateHandle: SavedStateHandle): S

    final override fun getState(): S {
        return getState(savedStateHandle)
    }
}