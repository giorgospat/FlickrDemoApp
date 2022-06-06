package com.patronas.flickr.di

import com.patronas.data.datasource.flickr.FlickrDataSource
import com.patronas.data.repository.FlickrRepository
import com.patronas.data.repository.FlickrRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideApiRepository(
        datasource: FlickrDataSource
    ): FlickrRepository {
        return FlickrRepositoryImpl(
            datasource = datasource
        )
    }

}