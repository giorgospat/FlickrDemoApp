package com.patronas.flickr.di

import com.patronas.flickr.BuildConfig
import com.patronas.network.api.ApiService
import com.patronas.network.builder.RetrofitBuilder
import com.patronas.network.config.NetworkConfiguration
import com.patronas.network.config.NetworkConstants
import com.patronas.network.config.NetworkConstants.connectTimeoutSeconds
import com.patronas.network.config.NetworkConstants.readTimeoutSeconds
import com.patronas.network.config.NetworkConstants.writeTimeoutSeconds
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideNetworkConfiguration(): NetworkConfiguration {
        return if (BuildConfig.SERVICE_CONFIG_LIVE) {
            NetworkConstants.getLiveConfiguration()
        } else {
            NetworkConstants.getTestConfiguration()
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            null
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        networkConfiguration: NetworkConfiguration,
        httpLoggingInterceptor: HttpLoggingInterceptor?
    ): ApiService {

        return RetrofitBuilder().makeRetrofitService(
            apiClass = ApiService::class.java,
            baseUrl = networkConfiguration.baseUrl,
            connectTimeoutSeconds = connectTimeoutSeconds,
            readTimeoutSeconds = readTimeoutSeconds,
            writeTimeoutSeconds = writeTimeoutSeconds,
            interceptors = listOf(httpLoggingInterceptor),
            moshi = moshi
        )
    }

}