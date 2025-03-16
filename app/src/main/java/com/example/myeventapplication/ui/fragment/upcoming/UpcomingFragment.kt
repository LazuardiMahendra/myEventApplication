package com.example.myeventapplication.ui.fragment.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.viewModel.MainViewModel
import com.example.myeventapplication.databinding.FragmentUpcomingBinding
import com.example.myeventapplication.ui.adapter.UpComingAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: UpComingAdapter

    private var keyword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        initUpComingEventAdapter()

        mainViewModel.events.observe(viewLifecycleOwner, { events ->
            adapter.submitList(events)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                keyword = searchView.text.toString()
                searchBar.setText(keyword)
                searchView.hide()
                Toast.makeText(requireContext(), keyword, Toast.LENGTH_SHORT).show()
                getUpComingEvent(keyword)
                true
            }
            false
        }
        getUpComingEvent(keyword)
    }

    private fun initUpComingEventAdapter() {
        adapter = UpComingAdapter()
        binding.rvUpComingEvent.adapter = adapter
        binding.rvUpComingEvent.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getUpComingEvent(keyword: String?) {
        mainViewModel.getUpComingEvent(keyword)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}