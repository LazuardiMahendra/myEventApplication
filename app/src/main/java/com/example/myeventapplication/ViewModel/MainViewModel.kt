package com.example.myeventapplication.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventapplication.data.EventData
import com.example.myeventapplication.repo.MainRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepo) : ViewModel() {

    private val _events = MutableLiveData<List<EventData>>()
    val events: LiveData<List<EventData>> = _events

    fun getUpComingEvent() {
        viewModelScope.launch {
            val eventList = repo.getUpComingEvent()
            _events.value = eventList
        }
    }


}