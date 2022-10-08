package ru.ar2code.mvilite_core

abstract class MviLiteSerializableStateViewModel<S>(
    final override val initialStateFactory: MviLiteSerializableStateInitialFactory<S>
) : MviLiteViewModel<S>(initialStateFactory) {

    protected fun serializeState(state: S?) {
        state?.let {
            initialStateFactory.stateSerializer.serialize(it)
        }
    }

}