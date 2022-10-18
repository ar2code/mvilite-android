package ru.ar2code.mvilite_core

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 */
fun <T> MutableStateFlow<T>.updateIfNotNull(reducerFunction: (T) -> T?) {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 *
 * @return previous state that was before update
 */
fun <T> MutableStateFlow<T>.updateIfNotNullAndGetPrevious(reducerFunction: (T) -> T?): T {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return prevValue
        }
    }
}

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 *
 * @return updated state value returned by [reducerFunction]
 */
fun <T> MutableStateFlow<T>.updateIfNotNullAndGetUpdated(reducerFunction: (T) -> T?): T? {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return nextValue
        }
    }
}

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 */
suspend fun <T> MutableStateFlow<T>.coUpdateIfNotNull(reducerFunction: suspend (T) -> T?) {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 *
 * @return previous state that was before update
 */
suspend fun <T> MutableStateFlow<T>.coUpdateIfNotNullAndGetPrevious(reducerFunction: suspend (T) -> T?): T {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return prevValue
        }
    }
}

/**
 * Updates the [MutableStateFlow.value] atomically using the specified [reducerFunction] of its value.
 *
 * If [reducerFunction] returns null, value will not be updated.
 *
 * [reducerFunction] may be evaluated multiple times, if [MutableStateFlow.value] is being concurrently updated.
 *
 * @return updated state value returned by [reducerFunction]
 */
suspend fun <T> MutableStateFlow<T>.coUpdateIfNotNullAndGetUpdated(reducerFunction: suspend (T) -> T?): T? {
    while (true) {
        val prevValue = value
        val nextValue = reducerFunction(prevValue)
        if (nextValue == null || compareAndSet(prevValue, nextValue)) {
            return nextValue
        }
    }
}