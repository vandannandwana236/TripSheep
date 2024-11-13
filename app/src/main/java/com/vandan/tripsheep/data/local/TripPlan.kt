package com.vandan.tripsheep.data.local

data class TripPlan (
    val id: Int,
    val days: Int,
    val description: String,
    val itinerary: String,
    val name: String,
    val placesToVisit: List<String>,
    val priceAdult: Double,
    val priceChild: Double,
    val priceOffer: Double,
    val tripImages: List<String>
)