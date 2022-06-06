package com.patronas.flickr.di

import com.patronas.data.datasource.flickr.FlickrDataSource
import com.patronas.data.datasource.flickr.FlickrDataSourceImpl
import com.patronas.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun provideFlickrDataSource(
        apiService: ApiService
    ): FlickrDataSource {
        return FlickrDataSourceImpl(apiService = apiService)
    }

}