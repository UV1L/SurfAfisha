package com.example.surfafisha.ui.main.Fragments

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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.surfafisha.DB.DAO.FilmDataBase
import com.example.surfafisha.IObserver
import com.example.surfafisha.ListFilmObserver
import com.example.surfafisha.Models.Film
import com.example.surfafisha.R
import com.example.surfafisha.VM.MainViewModel
import com.example.surfafisha.ui.main.ViewModelFactory
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerViewFragment : Fragment(), IObserver {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var db: FilmDataBase
    private val viewModel: MainViewModel by viewModels { ViewModelFactory(db) }

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
        db = Room.databaseBuilder(
            requireContext(),
            FilmDataBase::class.java,
            "FilmReader"
        ).allowMainThreadQueries().build() //так делать нехорошо, я знаю

        recyclerView = requireView().findViewById(R.id.fragment_main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        filmAdapter = ListFilmAdapter()
        recyclerView.adapter = filmAdapter

        onObserveAll()

        viewModel.startQuery()

        val searchEditTxt = parentFragment?.view?.findViewById<EditText>(R.id.search_edit_text)
        searchEditTxt?.afterTextChanged {
            val clearIcon = parentFragment?.view?.findViewById<ImageView>(R.id.clear_icon)
            if (it != "") {
                clearIcon?.visibility = ImageView.VISIBLE
                viewModel.startFilterQuery(it)
            } else {
                if (viewModel.data.value?.isEmpty() == true) {
                    parentFragment?.parentFragmentManager?.popBackStack()
                    val progressIndicator = requireView()
                        .findViewById<CircularProgressIndicator>(R.id.fragment_main_progress_bar)
                    progressIndicator.visibility = CircularProgressIndicator.VISIBLE
                    recyclerView.visibility = RecyclerView.INVISIBLE
                }
                viewModel.startQuery()
                clearIcon?.visibility = ImageView.INVISIBLE
            }
        }
    }

    override fun <T> update(o: T) {
        if (o is Film) viewModel.dbUpdate(o as Film)
        if (o is Fragment) setFragmentIfNoData(o)
    }

    private fun setFragmentIfNoData(fragment: Fragment) {
        parentFragment?.parentFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commitNow()
    }

    private fun onObserveAll() {
        filmAdapter.addObserver(this)

        viewModel.addObserver(this)

        viewModel.data.observe(viewLifecycleOwner, ListFilmObserver(requireContext(), filmAdapter))

        viewModel.readyToShow.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val progressIndicator = requireView()
                    .findViewById<CircularProgressIndicator>(R.id.fragment_main_progress_bar)
                progressIndicator.visibility = CircularProgressIndicator.INVISIBLE
                recyclerView.visibility = RecyclerView.VISIBLE
            }
        })

        viewModel.clearAdapter.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                filmAdapter.clear()
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