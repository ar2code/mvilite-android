package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle

/**
 * Factory that has access to ViewModel's [SavedStateHandle] and deserialize state automatically
 */
abstract class MviLiteSerializableStateInitialFactory<S>(
    private val stateDeserializer: StateDeserializer<S>,
    internal val stateSerializer: StateSerializer<S>,
    savedStateHandle: SavedStateHandle
) :
    MviLiteSavedStateHandleInitialFactory<S>(savedStateHandle) {

    abstract fun getStateOnDeserializationFailed(savedStateHandle: SavedStateHandle): S

    final override fun getInitialState(savedStateHandle: SavedStateHandle): S {
        return try {
            stateDeserializer.deserialize()
        } catch (e: Exception) {
            getStateOnDeserializationFailed(savedStateHandle)
        }
    }
}