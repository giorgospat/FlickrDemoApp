package com.patronas.flickr.presentation.ui.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.patronas.domain.model.PhotoDomainModel
import com.patronas.domain.usecase.SearchPhotosUseCase
import com.patronas.flickr.presentation.ui.screens.screens.home.MainViewModel
import com.patronas.flickr.presentation.utils.DispatcherProviderImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchPhotosUseCase: SearchPhotosUseCase

    private lateinit var viewModel: MainViewModel
    private val dispatcher = DispatcherProviderImpl(
        mainThread = Dispatchers.Unconfined,
        backgroundThread = Dispatchers.Unconfined,
        default = Dispatchers.Unconfined
    )


    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        viewModel =
            MainViewModel(searchPhotosUseCase = searchPhotosUseCase, dispatcher = dispatcher)
    }

    @Test
    fun `given start fetching images button is pressed verify viewModel emit api images to View`() {
        runTest {

            val apiPhotos = flow {
                emit(PhotoDomainModel(url = "url1", title = "title1"))
                emit(PhotoDomainModel(url = "url2", title = "title2"))
            }

            Mockito.`when`(
                searchPhotosUseCase.photos()
            ).thenReturn(
                apiPhotos
            )

            viewModel.setServiceStatus(true)
            viewModel.observeService()
            assertEquals(viewModel.photos.value.size, 2)
            assertEquals(viewModel.photos.value, listOf(apiPhotos.last(), apiPhotos.first()))

        }
    }

    @Test
    fun `given service status set false, verify serviceStarted value is false`() {
        viewModel.setServiceStatus(started = false)
        assertEquals(viewModel.serviceStarted.value, false)
    }

    @Test
    fun `given service status set true, verify serviceStarted value is true`() {
        viewModel.setServiceStatus(started = true)
        assertEquals(viewModel.serviceStarted.value, true)
    }

}