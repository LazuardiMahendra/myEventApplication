package com.example.myeventapplication.data

import com.google.gson.annotations.SerializedName

data class EventResponse(
    val error: Boolean,
    val message: String,
    val listEvents: List<EventData>
)

data class EventSingleResponse(
    val error: Boolean,
    val message: String,
    val event: EventData
)

data class EventData(
    val id: Int,
    val name: String,
    val summary: String,
    val description: String,
    val imageLogo: String,
    val mediaCover: String,
    val category: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
    val endTime: String,
    val link: String
) {
    companion object {
        fun default() = EventData(
            id = 0,
            name = "error",
            summary = "error",
            description = "error",
            imageLogo = "error",
            mediaCover = "error",
            category = "error",
            ownerName = "error",
            cityName = "error",
            quota = 0,
            registrants = 0,
            beginTime = "error",
            endTime = "error",
            link = "error"
        )
    }
}
