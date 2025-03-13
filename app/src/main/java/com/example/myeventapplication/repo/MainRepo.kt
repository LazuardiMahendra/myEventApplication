package com.example.myeventapplication.repo

import com.example.myeventapplication.data.EventData
import com.example.myeventapplication.data.EventResponse
import com.example.myeventapplication.network.ApiService


class MainRepo(private val apiService: ApiService) {
    suspend fun getUpComingEvent(active: Int, query: String?, limit: Int?): List<EventData> {
        val response = apiService.getAllEvent(active, query, limit)
        return if (!response.error) response.listEvents else emptyList()
    }

    suspend fun getDoneEvent(active: Int, query: String?, limit: Int?): List<EventData> {
        val response = apiService.getAllEvent(active, query, limit)
        return if (!response.error) response.listEvents else emptyList()
    }

    suspend fun getAllEvent(active: Int, query: String?, limit: Int?): List<EventData> {
        val response = apiService.getAllEvent(active, query, limit)
        return if (!response.error) response.listEvents else emptyList()
    }
}