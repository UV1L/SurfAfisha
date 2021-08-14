package com.example.surfafisha.ui.main.Fragments

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.surfafisha.MainActivity
import com.example.surfafisha.R


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

        val mgr = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
}