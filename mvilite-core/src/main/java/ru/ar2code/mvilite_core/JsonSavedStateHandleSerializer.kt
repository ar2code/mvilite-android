package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

class JsonSavedStateHandleSerializer<S>(
    private val savedStateHandle: SavedStateHandle,
    private val stateSerializationKey: String,
    private val stateSerializationStrategy: SerializationStrategy<S>
) : StateSerializer<S> {

    override fun serialize(state: S) {
        savedStateHandle[stateSerializationKey] =
            Json.encodeToString(stateSerializationStrategy, state)
    }
}