package ru.ar2code.mvilite_core

interface StateDeserializer<S> {

    @Throws
    fun deserialize(): S

}