package com.example.surfafisha

interface IObserver {
    fun <T> update(o: T)
}