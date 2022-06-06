package com.patronas.flickr.di

import com.patronas.flickr.presentation.utils.DispatcherProvider
import com.patronas.flickr.presentation.utils.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl(
            mainThread = Dispatchers.Main,
            backgroundThread = Dispatchers.IO,
            default = Dispatchers.Default
        )
    }

}