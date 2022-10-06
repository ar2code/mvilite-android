package ru.ar2code.mvilite_core

interface StateSerializer<S> {

    @Throws
    fun serialize(state: S)

}