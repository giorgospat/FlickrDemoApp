package com.patronas.domain.usecase

import com.patronas.data.base.DomainApiResult
import com.patronas.data.repository.FlickrRepository
import com.patronas.domain.mapper.PhotosMapper
import com.patronas.domain.model.PhotoDomainModel
import com.patronas.network.model.SearchPhotosApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class SearchPhotosUseCaseImpl(
    private val repo: FlickrRepository,
    private val mapper: PhotosMapper
) : SearchPhotosUseCase {

    //flow to update our view with new images
    private val _photos = MutableSharedFlow<PhotoDomainModel>()
    private val photos: Flow<PhotoDomainModel> = _photos

    private val media = "photos"
    private val accuracy = 11 // city
    private val radius = 0.1 // 100 meters from user location
    private val perPage = 1
    private val page = 1

    override suspend fun searchPhotos(lat: Double, lon: Double) {
        when (val response = repo.searchPhotos(
            request = SearchPhotosApiRequest(
                media = media,
                lat = lat,
                lon = lon,
                accuracy = accuracy,
                radius = radius,
                perPage = perPage,
                page = page
            )
        )
        ) {
            is DomainApiResult.Success -> {
                val domainModel = mapper.mapToDomain(response.data)
                _photos.emit(domainModel)
            }
            is DomainApiResult.Error -> {
            }
        }
    }

    override fun photos(): Flow<PhotoDomainModel> {
        return photos
    }

}