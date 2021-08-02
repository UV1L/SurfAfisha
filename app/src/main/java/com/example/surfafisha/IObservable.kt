package com.example.surfafisha

interface IObservable {
    val observers: ArrayList<IObserver>

    fun addObserver(observer: IObserver) {
        observers.add(observer)
    }

    fun remove(observer: IObserver) {
        observers.remove(observer)
    }

    fun <T> sendUpdateEvent(o: T) {
        observers.forEach { it.update(o) }
    }
}