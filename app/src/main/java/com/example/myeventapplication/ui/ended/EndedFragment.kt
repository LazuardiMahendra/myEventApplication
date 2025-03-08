package com.example.myeventapplication.ui.ended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeventapplication.ViewModel.MainViewModel
import com.example.myeventapplication.databinding.FragmentEndedBinding
import com.example.myeventapplication.ui.adapter.DoneAdapter
import com.example.myeventapplication.ui.adapter.UpComingAdapter

class EndedFragment : Fragment() {

    private var _binding: FragmentEndedBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: DoneAdapter

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

        getDoneEvent()
    }

    private fun getDoneEvent() {
        mainViewModel.getDoneEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}