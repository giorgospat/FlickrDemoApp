package com.patronas.domain.usecase

import com.patronas.domain.model.PhotoDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface SearchPhotosUseCase {
    suspend fun searchPhotos(lat: Double, lon: Double)
    fun photos(): Flow<PhotoDomainModel>
}