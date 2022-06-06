package com.patronas.data.repository

import com.patronas.data.base.DomainApiResult
import com.patronas.data.datasource.flickr.FlickrDataSource
import com.patronas.network.model.SearchPhotosApiRequest
import com.patronas.network.model.SearchPhotosApiResponse

class FlickrRepositoryImpl(
    private val datasource: FlickrDataSource
) : FlickrRepository {

    override suspend fun searchPhotos(request: SearchPhotosApiRequest): DomainApiResult<SearchPhotosApiResponse> {
        return when (val data = datasource.searchPhotos(request = request)
        ) {
            is DomainApiResult.Success -> {
                DomainApiResult.Success(data.data)
            }
            is DomainApiResult.Error -> {
                DomainApiResult.Error()
            }
        }
    }

}