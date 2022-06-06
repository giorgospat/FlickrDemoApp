package com.patronas.network.api

import com.patronas.network.config.NetworkConstants.FLICKR_API_KEY
import com.patronas.network.config.NetworkConstants.noJsonCallback
import com.patronas.network.config.NetworkConstants.responseFormat
import com.patronas.network.model.SearchPhotosApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("rest/?method=flickr.photos.search")
    suspend fun searchPhotos(
        @Query("api_key") apiKey: String = FLICKR_API_KEY,
        @Query("format") format: String = responseFormat,
        @Query("nojsoncallback") jsonCallback: Int = noJsonCallback,
        @Query("media") media: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("accuracy") accuracy: Int,
        @Query("radius") radius: Double,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchPhotosApiResponse>

}