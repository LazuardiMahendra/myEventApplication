package com.example.myeventapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myeventapplication.data.EventData
import com.example.myeventapplication.databinding.ItemEventBinding
import com.example.myeventapplication.ui.fragment.home.HomeFragmentDirections
import com.example.myeventapplication.ui.fragment.upcoming.UpcomingFragmentDirections

class TopDoneAdapter : ListAdapter<EventData, TopDoneAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: EventData) {
            binding.tvTitle.text = model.name
            binding.tvSummary.text = model.summary
            Glide.with(itemView.context).load(model.imageLogo).into(binding.ivPoster)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val bind = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(bind)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionNavHomeToDetailEventActivity(event.id.toString()))
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventData>() {
            override fun areItemsTheSame(
                oldItem: EventData, newItem: EventData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EventData, newItem: EventData
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}