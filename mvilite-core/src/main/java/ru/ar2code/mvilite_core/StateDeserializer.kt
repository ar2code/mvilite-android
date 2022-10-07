package ru.ar2code.mvilite_core

interface StateDeserializer<S> {

    fun deserialize(): S

}