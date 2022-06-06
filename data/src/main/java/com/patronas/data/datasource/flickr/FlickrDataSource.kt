package com.patronas.data.datasource.flickr

import com.patronas.data.base.DomainApiResult
import com.patronas.network.model.SearchPhotosApiRequest
import com.patronas.network.model.SearchPhotosApiResponse

interface FlickrDataSource {
    suspend fun searchPhotos(request: SearchPhotosApiRequest): DomainApiResult<SearchPhotosApiResponse>
}