package com.patronas.data.repository

import com.patronas.data.base.DomainApiResult
import com.patronas.network.model.SearchPhotosApiRequest
import com.patronas.network.model.SearchPhotosApiResponse

interface FlickrRepository {
    suspend fun searchPhotos(request: SearchPhotosApiRequest): DomainApiResult<SearchPhotosApiResponse>
}