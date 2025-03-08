package com.example.myeventapplication.repo

import com.example.myeventapplication.data.EventResponse

interface MainDataSource {
    fun getUpcomingEvent(
        onSuccess: (EventResponse) -> Unit, onError: (Throwable) -> Unit
    )
}