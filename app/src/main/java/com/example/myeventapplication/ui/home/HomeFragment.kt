package com.example.myeventapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.ViewModel.MainViewModel
import com.example.myeventapplication.databinding.FragmentHomeBinding
import com.example.myeventapplication.ui.adapter.TopDoneAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: TopDoneAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initTopDoneEvent()

        mainViewModel.events.observe(viewLifecycleOwner, { events ->
            adapter.submitList(events)
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTopDoneEvent()
    }

    private fun initTopDoneEvent() {
        adapter = TopDoneAdapter()
        binding.rvDone.adapter = adapter
        binding.rvDone.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getTopDoneEvent() {
        mainViewModel.getTopDoneEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}