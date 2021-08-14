package com.example.surfafisha.ui.main.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.surfafisha.*
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.data.DB.DAO.FilmDataBase
import com.example.data.Models.FilmNet
import com.example.surfafisha.VM.MainViewModel
import com.example.surfafisha.ui.main.ViewModelFactory
import com.google.android.material.progressindicator.CircularProgressIndicator

class RecyclerViewFragment : BaseFragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private val viewModel: MainViewModel by viewModels { ViewModelFactory() }

    override val eventObservable: ViewEventObservable by lazy { viewModel }

    private lateinit var filmAdapter: ListFilmAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.fragment_main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        filmAdapter = ListFilmAdapter()
        recyclerView.adapter = filmAdapter

        onObserveAll()

        viewModel.getData()

//        val searchEditTxt = parentFragment?.view?.findViewById<EditText>(R.id.search_edit_text)
//        val clearIcon = parentFragment?.view?.findViewById<ImageView>(R.id.clear_icon)
//        searchEditTxt?.afterTextChanged {
//            if (it != "") {
//                clearIcon?.visibility = ImageView.VISIBLE
//                viewModel.startQuery(it)
//            } else {
//                if (viewModel.data.value?.isEmpty() == true) {
//                    parentFragment?.parentFragmentManager?.popBackStack()
//                    val progressIndicator = requireView()
//                        .findViewById<CircularProgressIndicator>(R.id.fragment_main_progress_bar)
//                    progressIndicator.visibility = CircularProgressIndicator.VISIBLE
//                    recyclerView.visibility = RecyclerView.INVISIBLE
//                }
//                viewModel.startQuery()
//                clearIcon?.visibility = ImageView.INVISIBLE
//            }
//        }

//        clearIcon?.setOnClickListener {
//            searchEditTxt?.setText("")
//        }
    }

//    override fun <T> update(o: T) {
//        when (o) {
//            is FilmNet -> viewModel.dbUpdate(o as FilmNet)
//            is Fragment -> setFragmentIfNoData(o)
//            is ListFilmObserver -> viewModel.clearData()
//        }
//    }

    private fun setFragmentIfNoData(fragment: Fragment) {
        parentFragment?.parentFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commitNow()
    }

    private fun onObserveAll() {

//        viewModel.addObserver(this)

        val listFilmObserver = ListFilmObserver(filmAdapter)

        viewModel.data.observe(viewLifecycleOwner, listFilmObserver)

        viewModel.readyToShow.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val progressIndicator = requireView()
                    .findViewById<CircularProgressIndicator>(R.id.fragment_main_progress_bar)
                progressIndicator.visibility = CircularProgressIndicator.INVISIBLE
                recyclerView.visibility = RecyclerView.VISIBLE
            }
        })
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}