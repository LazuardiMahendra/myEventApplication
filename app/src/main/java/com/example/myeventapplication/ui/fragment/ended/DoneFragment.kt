package com.example.myeventapplication.ui.fragment.ended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.viewModel.MainViewModel
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEndedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initDoneEventAdapter()

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        mainViewModel.events.observe(viewLifecycleOwner) { event ->
            adapter.submitList(event)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
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

    private fun initDoneEventAdapter() {
        adapter = DoneAdapter()
        binding.rvDoneEvent.adapter = adapter
        binding.rvDoneEvent.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getDoneEvent(keyword: String?) {
        mainViewModel.getDoneEvent(keyword)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}