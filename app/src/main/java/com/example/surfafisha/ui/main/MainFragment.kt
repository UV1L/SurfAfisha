package com.example.surfafisha.ui.main

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.surfafisha.DB.DAO.FilmDataBase
import com.example.surfafisha.DB.FilmsDB
import com.example.surfafisha.ListFilmObserver
import com.example.surfafisha.MainActivity
import com.example.surfafisha.Models.Film
import com.example.surfafisha.R
import com.example.surfafisha.VM.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels { ViewModelFactory(db) }
    private lateinit var db: FilmsDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val activityMain = activity as? MainActivity
        activityMain?.supportActionBar?.hide()

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        db = FilmsDB(requireContext(), "filmsDB")
        viewModel.createDB(requireContext())

        val recyclerView = view?.findViewById<RecyclerView>(R.id.fragment_main_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val filmAdapter = ListFilmAdapter()
        recyclerView?.adapter = filmAdapter
        viewModel.parseJson()

        viewModel.data.observe(viewLifecycleOwner, ListFilmObserver(requireContext(), filmAdapter))
    }
}