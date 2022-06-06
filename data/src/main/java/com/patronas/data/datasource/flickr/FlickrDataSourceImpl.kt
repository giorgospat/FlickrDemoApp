package com.patronas.data.datasource.flickr

import com.patronas.data.base.DomainApiResult
import com.patronas.data.datasource.base.BaseDatasource
import com.patronas.network.api.ApiService
import com.patronas.network.model.SearchPhotosApiRequest
import com.patronas.network.model.SearchPhotosApiResponse

class FlickrDataSourceImpl(
    private val apiService: ApiService
) : FlickrDataSource,
    BaseDatasource() {
    override suspend fun searchPhotos(request: SearchPhotosApiRequest): DomainApiResult<SearchPhotosApiResponse> {
        return try {
            execute(
                with(request) {
                    apiService.searchPhotos(
                        media = media,
                        lat = lat,
                        lon = lon,
                        accuracy = accuracy,
                        radius = radius,
                        perPage = perPage,
                        page = page
                    )
                }
            )
        } catch (e: Throwable) {
            DomainApiResult.Error()
        }
    }

}