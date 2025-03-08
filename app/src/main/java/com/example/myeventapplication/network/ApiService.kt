package com.example.myeventapplication.network

import com.example.myeventapplication.data.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Routing.GET_ALL_EVENT_URL)
    suspend fun getAllEvent(
        @Query("active") active: Int,
    ): EventResponse

    @GET(Routing.GET_SINGLE_EVENT_URL)
    fun getSingleEvent(@Path("event_id") event_id: String): Call<EventResponse>

}