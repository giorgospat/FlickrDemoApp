package com.patronas.flickr.di

import com.patronas.domain.mapper.PhotosMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainMapperModule {

    @Provides
    @Singleton
    fun providePhotosMapper(): PhotosMapper {
        return PhotosMapper()
    }

}