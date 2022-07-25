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
    abstract fun getEmptyState(savedStateHandle: SavedStateHandle): S

    /**
     * Load initial state from [savedStateHandle] with coroutine
     */
    open suspend fun loadState(savedStateHandle: SavedStateHandle): S? {
        return null
    }

    final override suspend fun loadState(): S? {
        return loadState(savedStateHandle)
    }

    final override fun getInitialState(): S {
        return getEmptyState(savedStateHandle)
    }
}