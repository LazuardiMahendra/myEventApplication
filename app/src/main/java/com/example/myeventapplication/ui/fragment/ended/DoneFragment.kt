package com.example.myeventapplication.ui.fragment.ended

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.ViewModel.MainViewModel
import com.example.myeventapplication.databinding.FragmentEndedBinding
import com.example.myeventapplication.ui.adapter.DoneAdapter

class DoneFragment : Fragment() {

    private var _binding: FragmentEndedBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: DoneAdapter

    private var keyword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEndedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = DoneAdapter()
        binding.rvDoneEvent.adapter = adapter
        binding.rvDoneEvent.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel.events.observe(viewLifecycleOwner, { event ->
            adapter.submitList(event)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                keyword = searchView.text.toString()
                searchBar.setText(keyword)
                searchView.hide()
                getDoneEvent(keyword)
                true
            }
            false
        }

        getDoneEvent(keyword)
    }

    private fun getDoneEvent(keyword: String?) {
        mainViewModel.getDoneEvent(keyword)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}