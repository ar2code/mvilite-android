package ru.ar2code.mvilite_core

abstract class MviLiteSerializableStateViewModel<S>(
    initialStateFactory: MviLiteSerializableStateInitialFactory<S>
) : MviLiteViewModel<S>(initialStateFactory) {

    protected fun serializeState(state: S?) {
        state?.let {
            if (initialStateFactory !is MviLiteSerializableStateInitialFactory) {
                throw IllegalArgumentException("initialStateFactory must be MviLiteSerializableStateInitialFactory.")
            }
            initialStateFactory.stateSerializer.serialize(it)
        }
    }

}