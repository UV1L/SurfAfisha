package com.example.surfafisha.ui.main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.surfafisha.DB.DAO.FilmDataBase
import com.example.surfafisha.IObserver
import com.example.surfafisha.ListFilmObserver
import com.example.surfafisha.MainActivity
import com.example.surfafisha.Models.Film
import com.example.surfafisha.R
import com.example.surfafisha.VM.MainViewModel
import com.example.surfafisha.ui.main.ViewModelFactory
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activityMain = activity as? MainActivity
        activityMain?.supportActionBar?.hide()

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container, RecyclerViewFragment.newInstance(), "recycler_view_fragment")
            .commitNow()
    }
}