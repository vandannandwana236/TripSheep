package com.vandan.tripsheep.data.dto

import com.vandan.tripsheep.data.local.TripPackage
import com.vandan.tripsheep.data.resource.TripPackageState

data class TripPackageDto(
    val tripPackageId:Long,
    val about: String,
    val hotels: List<HotelDto>,
    val imageUrls: List<String>,
    val packageName: String
)

fun TripPackageDto.toTripPackage():TripPackage{
    return TripPackage(
        tripPackageId = tripPackageId,
        about = about,
        hotels = hotels.map { it.toHotel() },
        imageUrls = imageUrls,
        packageName = packageName
    )
}