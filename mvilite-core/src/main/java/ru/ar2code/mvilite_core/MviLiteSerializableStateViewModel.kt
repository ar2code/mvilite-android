package ru.ar2code.mvilite_core

abstract class MviLiteSerializableStateViewModel<S>(
    private val stateSerializer: StateSerializer<S>,
    initialStateFactory: MviLiteInitialStateFactory<S>
) : MviLiteViewModel<S>(initialStateFactory) {

    protected fun serializeState(state: S?) {
        state?.let {
            stateSerializer.serialize(it)
        }
    }

}