package com.example.myeventapplication.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventapplication.data.EventData
import com.example.myeventapplication.repo.MainRepo
import com.example.myeventapplication.utils.NetworkUtils
import kotlinx.coroutines.launch
import okhttp3.internal.EMPTY_REQUEST

class MainViewModel(private val repo: MainRepo, private val context: Context) : ViewModel() {

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

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun getUpComingEvent(keyword: String?) {
        viewModelScope.launch {
            if (!NetworkUtils.checkConnection(context)) {
                _errorMessage.value = "Tidak Ada Koneksi Internet"
                return@launch
            }
            _isLoading.value = true
            try {
                val eventList = repo.getUpComingEvent(1, keyword, null)
                _events.value = eventList
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getDoneEvent(keyword: String?) {
        viewModelScope.launch {
            if (!NetworkUtils.checkConnection(context)) {
                _errorMessage.value = "Tidak Ada Koneksi Internet"
                return@launch
            }
            _isLoading.value = true
            try {
                val eventList = repo.getDoneEvent(0, keyword, null)
                _events.value = eventList
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTopUpComingEvent() {
        viewModelScope.launch {
            if (!NetworkUtils.checkConnection(context)) {
                _errorMessage.value = "Tidak Ada Koneksi Internet"
                return@launch
            }
            _isLoading.value = true
            try {
                val eventList = repo.getAllEvent(1, null, 5)
                _upComingEvent.value = eventList
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTopDoneEvent() {
        viewModelScope.launch {
            if (!NetworkUtils.checkConnection(context)) {
                _errorMessage.value = "Tidak Ada Koneksi Internet"
                return@launch
            }
            _isLoading.value = true
            try {
                val eventList = repo.getAllEvent(0, null, 5)
                if (eventList != null) {
                    _doneEvent.value = eventList
                } else {
                    _errorMessage.value = "Data tidak ditemukan"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan : ${e.toString()}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getDetailEvent(eventId: String) {
        viewModelScope.launch {
            if (!NetworkUtils.checkConnection(context)) {
                _errorMessage.value = "Tidak Ada Koneksi Internet"
                return@launch
            }
            _isLoading.value = true
            try {
                val eventList = repo.getDetailEvent(eventId)
                _eventsSingle.value = eventList
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


}