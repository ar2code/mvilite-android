package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

class JsonSavedStateHandleSerializer<S>(
    private val savedStateHandle: SavedStateHandle,
    private val stateSerializationKey: String,
    private val stateSerializationStrategy: SerializationStrategy<S>
) : StateSerializer<S> {

    /**
     * Serializes the [state] into an equivalent JSON using the given [stateSerializationStrategy]
     * and store into a savedState [savedStateHandle] with key [stateSerializationKey].
     *
     * @throws [SerializationException] if the given value cannot be serialized to JSON.
     */
    override fun serialize(state: S) {
        savedStateHandle[stateSerializationKey] =
            Json.encodeToString(stateSerializationStrategy, state)
    }
}