package com.example.getwell.screens.resourcesection.wellnesscentersfinder.data

import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassApiService {
    @GET("api/interpreter")
    suspend fun searchNearbyWellnessCenters(
        @Query("data") query: String
    ): OverpassResponse
}

data class OverpassResponse(
    val elements: List<OverpassElement>
)

data class OverpassElement(
    val id: Long,
    val type: String,
    val lat: Double,
    val lon: Double,
    val tags: Map<String, String>
)