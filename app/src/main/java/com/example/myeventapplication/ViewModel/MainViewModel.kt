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

    private val _upComingEvent = MutableLiveData<List<EventData>>()
    val upComingEvent: LiveData<List<EventData>> = _upComingEvent

    private val _doneEvent = MutableLiveData<List<EventData>>()
    val doneEvent: LiveData<List<EventData>> = _doneEvent

    private val _eventsSingle = MutableLiveData<EventData>()
    val eventsSingle: LiveData<EventData> = _eventsSingle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUpComingEvent(keyword: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = repo.getUpComingEvent(1, keyword, null)
            _events.value = eventList
            _isLoading.value = false
        }
    }

    fun getDoneEvent(keyword: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = repo.getDoneEvent(0, keyword, null)
            _events.value = eventList
            _isLoading.value = false
        }
    }

    fun getTopUpComingEvent() {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = repo.getAllEvent(1, null, 5)
            _upComingEvent.value = eventList
            _isLoading.value = false
        }
    }

    fun getTopDoneEvent() {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = repo.getAllEvent(0, null, 5)
            _doneEvent.value = eventList
            _isLoading.value = false
        }
    }

    fun getDetailEvent(eventId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = repo.getDetailEvent(eventId)
            _eventsSingle.value = eventList
            _isLoading.value = false
        }
    }


}