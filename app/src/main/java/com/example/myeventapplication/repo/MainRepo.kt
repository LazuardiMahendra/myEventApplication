package com.example.myeventapplication.repo

import com.example.myeventapplication.data.EventData
import com.example.myeventapplication.data.EventResponse
import com.example.myeventapplication.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class MainRepo(private val apiService: ApiService) {
    suspend fun getUpComingEvent(): List<EventData> {
        val response = apiService.getAllEvent(active = 0)
        return if (!response.error) response.listEvents else emptyList()
    }

    suspend fun getDoneEvent(): List<EventData> {
        val response = apiService.getAllEvent(active = 0)
        return if (!response.error) response.listEvents else emptyList()
    }

    suspend fun getAllEvent(): List<EventData> {
        val response = apiService.getAllEvent(active = -1)
        return if (!response.error) response.listEvents else emptyList()
    }
}