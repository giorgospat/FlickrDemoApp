package com.patronas.flickr.di

import com.patronas.data.repository.FlickrRepository
import com.patronas.domain.mapper.PhotosMapper
import com.patronas.domain.usecase.SearchPhotosUseCase
import com.patronas.domain.usecase.SearchPhotosUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideSearchPhotosUseCase(
        repo: FlickrRepository,
        mapper: PhotosMapper
    ): SearchPhotosUseCase {
        return SearchPhotosUseCaseImpl(repo = repo, mapper = mapper)
    }

}