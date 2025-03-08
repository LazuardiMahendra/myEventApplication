package com.example.myeventapplication.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.ViewModel.MainViewModel
import com.example.myeventapplication.databinding.FragmentUpcomingBinding
import com.example.myeventapplication.ui.adapter.UpComingAdapter
import retrofit2.http.Tag
import org.koin.androidx.viewmodel.ext.android.viewModel


class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: UpComingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        adapter = UpComingAdapter()
        binding.rvUpComingEvent.adapter = adapter
        binding.rvUpComingEvent.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel.events.observe(viewLifecycleOwner, { events ->
            adapter.submitList(events)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUpComingEvent()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                Toast.makeText(requireContext(), searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private fun getUpComingEvent() {
        mainViewModel.getUpComingEvent()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}