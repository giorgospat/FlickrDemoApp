package com.patronas.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhotosApiRequest(
    @Json(name = "media")
    val media: String,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "accuracy")
    val accuracy: Int,
    @Json(name = "radius")
    val radius: Double,
    @Json(name = "per_page")
    val perPage: Int,
    @Json(name = "page")
    val page: Int
)