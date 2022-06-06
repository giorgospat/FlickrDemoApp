package com.patronas.network.model

import com.patronas.network.model.base.BaseApiResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhotosApiResponse(
    @Json(name = "photos")
    val photos: Photos?
) : BaseApiResponse()

@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "photo")
    val photos: List<Photo>?
)

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "id")
    val photoId: String?,
    @Json(name = "secret")
    val secret: String?,
    @Json(name = "server")
    val server: String?,
    @Json(name = "title")
    val title: String?
)