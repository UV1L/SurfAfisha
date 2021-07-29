package com.example.surfafisha.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.MainActivity
import com.example.surfafisha.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val activityMain = activity as? MainActivity
        activityMain?.supportActionBar?.hide()

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.fragment_main_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = ListFilmAdapter(viewModel.data)
        viewModel.parseJson()
    }

}