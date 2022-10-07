package ru.ar2code.mvilite_core

interface StateSerializer<S> {

    fun serialize(state: S)

}