package com.example.myeventapplication.network

import com.example.myeventapplication.data.EventResponse
import com.example.myeventapplication.data.EventSingleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Routing.GET_ALL_EVENT_URL)
    suspend fun getAllEvent(
        @Query("active") active: Int,
        @Query("q") keyword: String? = null,
        @Query("limit") limit: Int? = null,
    ): EventResponse

    @GET(Routing.GET_SINGLE_EVENT_URL)
    suspend fun getSingleEvent(@Path("event_id") event_id: String): EventSingleResponse
}