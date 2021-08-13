package com.example.surfafisha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

interface ViewEvent {

    fun execute(fragment: Fragment)
}

class ChangeColorStatusBarViewEvent : ViewEvent {

    override fun execute(fragment: Fragment) {

        fragment.requireActivity().window.statusBarColor = 1
    }
}

interface ViewEventObservable {

    val event: LiveData<ViewEvent>
}

abstract class BaseViewModel : ViewModel(), ViewEventObservable {

    private val _event = MutableLiveData<ViewEvent>()

    override val event: LiveData<ViewEvent> = _event

    fun postViewEvent(event : ViewEvent) {

        _event.value = event
    }
}

abstract class BaseFragment : Fragment() {

    abstract val eventObservable: ViewEventObservable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventObservable.event.observe(viewLifecycleOwner, Observer { it.execute(this) })
    }
}