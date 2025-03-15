package com.example.myeventapplication.ui.fragment.home

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
import com.example.myeventapplication.ui.adapter.TopUpComingAdapter
import com.google.android.material.carousel.CarouselLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var topDoneAdapter: TopDoneAdapter
    private lateinit var topUpComingAdapter: TopUpComingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        mainViewModel.doneEvent.observe(viewLifecycleOwner) { events ->
            topDoneAdapter.submitList(events)
        }

        mainViewModel.upComingEvent.observe(viewLifecycleOwner) { events ->
            topUpComingAdapter.submitList(events)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTopDoneEvent()
        getTopUpComingEvent()

        initTopDoneEvent()
        initTopUpComingEvent()
    }

    private fun initTopDoneEvent() {
        topDoneAdapter = TopDoneAdapter()
        binding.rvDone.adapter = topDoneAdapter
        binding.rvDone.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getTopDoneEvent() {
        mainViewModel.getTopDoneEvent()
    }

    private fun initTopUpComingEvent() {
        topUpComingAdapter = TopUpComingAdapter()
        binding.rvUpComing.adapter = topUpComingAdapter
        binding.rvUpComing.setLayoutManager(CarouselLayoutManager())

    }

    private fun getTopUpComingEvent() {
        mainViewModel.getTopUpComingEvent()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}