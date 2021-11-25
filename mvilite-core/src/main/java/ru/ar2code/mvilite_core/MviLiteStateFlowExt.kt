package ru.ar2code.mvilite_core

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 */
inline fun <T> MutableStateFlow<T>.updateIfNotNull(reducerFunction: (T) -> T?) {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}

inline fun <T> MutableStateFlow<T>.updateIfNotNullAndGetPrevious(reducerFunction: (T) -> T?): T {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return prevValue
        }
    }
}

inline fun <T> MutableStateFlow<T>.updateIfNotNullAndGetUpdated(reducerFunction: (T) -> T?): T? {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return nextValue
        }
    }
}