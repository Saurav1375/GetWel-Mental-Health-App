package com.example.getwell.screens.resourcesection.wellnesscentersfinder.data

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt



class WellnessCenterRepository {

    private val api = Retrofit.Builder()
        .baseUrl("https://overpass-api.de/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OverpassApiService::class.java)

    suspend fun getNearbyWellnessCenters(lat: Double, lon: Double, radiusMeters: Int = 5000): List<WellnessCenter> {
        val query = """
            [out:json];
            (
                node["healthcare"="hospital"](around:$radiusMeters,$lat,$lon);
                way["amenity"="hospital"](around:$radiusMeters,$lat,$lon);
                relation["amenity"="hospital"](around:$radiusMeters,$lat,$lon);
            );
            out body;
            >;
            out skel qt;
        """.trimIndent()

        return try {
            val response = api.searchNearbyWellnessCenters(query)
            Log.d("WellnessCenterRepository", "Total Elements Found: ${response.elements.size}")

            // Safely handle the response and create WellnessCenter objects
            response.elements.mapNotNull { element ->
                // Log element details
                Log.d("WellnessCenterRepository", "Element Details: ID=${element.id}, Tags=${element.tags}")

                // Handle missing lat/lon by returning null if both are missing
                val elementLat = element.lat ?: return@mapNotNull null
                val elementLon = element.lon ?: return@mapNotNull null

                WellnessCenter(
                    id = element.id.toString(),
                    name = element.tags?.get("name") ?: "Unknown Name",  // Safe call with fallback value
                    latitude = elementLat,
                    longitude = elementLon,
                    address = buildAddress(element.tags),  // Build address from tags
                    type = element.tags?.get("healthcare:speciality")
                        ?: element.tags?.get("amenity")
                        ?: "Healthcare Facility",  // Fallback type
                    distance = calculateDistance(lat, lon, elementLat, elementLon)
                )
            }

        } catch (e: Exception) {
            Log.e("WellnessCenterRepository", "Search Error", e)
            emptyList()
        }
    }

    // Enhanced address builder
    private fun buildAddress(tags: Map<String, String>?): String {
        return listOfNotNull(
            tags?.get("addr:housenumber"),
            tags?.get("addr:street"),
            tags?.get("addr:city"),
            tags?.get("addr:state"),
            tags?.get("addr:postcode")
        ).joinToString(", ").ifEmpty { "Address Not Available" }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return (results[0] / 100.0).roundToInt() / 10.0
    }
}
