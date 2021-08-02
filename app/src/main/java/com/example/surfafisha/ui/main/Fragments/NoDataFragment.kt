package com.example.surfafisha.ui.main.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.surfafisha.R

class NoDataFragment : Fragment() {

    companion object {
        fun newInstance() = NoDataFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.refresh_icon).setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RecyclerViewFragment.newInstance()) //не нужно создавать новый инстанс, но что-то с popBackStack не получилось
                    .commit()
            }
    }
}