package ru.ar2code.mvilite_core

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

class JsonSavedStateHandleDeserializer<S>(
    private val stateSerializationKey: String,
    private val deserializer: DeserializationStrategy<S>,
    private val savedStateHandle: SavedStateHandle
) : StateDeserializer<S> {
    override fun deserialize(): S {
        val state = savedStateHandle.get<String>(stateSerializationKey).orEmpty()
        return Json.decodeFromString(deserializer, state)
    }
}