package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class JsonSavedStateHandleDeserializer<S>(
    private val savedStateHandle: SavedStateHandle,
    private val stateSerializationKey: String,
    private val deserializer: DeserializationStrategy<S>
) : StateDeserializer<S> {

    /**
     * Gets JSON from savedState [savedStateHandle] by key [stateSerializationKey] and deserializes into a value of type [S] using the given [deserializer]
     *
     * @throws [SerializationException] if the given JSON string is not a valid JSON input for the type [S]
     * @throws [IllegalArgumentException] if the decoded input cannot be represented as a valid instance of type [S]
     */
    override fun deserialize(): S {
        val state = savedStateHandle.get<String>(stateSerializationKey).orEmpty()
        return Json.decodeFromString(deserializer, state)
    }
}