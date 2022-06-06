package com.patronas.data.repository

import com.patronas.data.datasource.flickr.FlickrDataSource
import com.patronas.data.datasource.flickr.FlickrDataSourceImpl
import com.patronas.network.api.ApiService
import com.patronas.network.config.NetworkConstants
import com.patronas.network.model.SearchPhotosApiRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class FlickrRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repo: FlickrRepository
    private lateinit var datasource: FlickrDataSource
    private val lat = 25.000000
    private val lon = 20.000000


    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        datasource = FlickrDataSourceImpl(apiService = apiService)
        repo = FlickrRepositoryImpl(datasource = datasource)
    }

    @Test
    fun `given searchPhotos request on repository, verify retrofit service is called`() {
        runTest {
            val apiRequest = SearchPhotosApiRequest(
                media = "photos",
                lat = lat,
                lon = lon,
                accuracy = 11,
                radius = 0.1,
                perPage = 1,
                page = 1
            )
            repo.searchPhotos(apiRequest)
            verify(apiService, times(1)).searchPhotos(
                apiKey = NetworkConstants.FLICKR_API_KEY,
                format = NetworkConstants.responseFormat,
                jsonCallback = NetworkConstants.noJsonCallback,
                media = "photos",
                lat = lat,
                lon = lon,
                accuracy = 11,
                radius = 0.1,
                perPage = 1,
                page = 1
            )
        }
    }

}
