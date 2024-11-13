package com.vandan.tripsheep.data.remote

import com.vandan.tripsheep.data.dto.TripPackageDto
import com.vandan.tripsheep.data.dto.TripPlanDto
import retrofit2.http.GET
//192.168.29.233
interface TripService {
    @GET("trips")
    suspend fun getTrips():List<TripPlanDto>
    @GET("getPackages")
    suspend fun getPackages():List<TripPackageDto>
}