package com.patronas.flickr.presentation.ui.screens.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patronas.domain.model.PhotoDomainModel
import com.patronas.domain.usecase.SearchPhotosUseCase
import com.patronas.flickr.presentation.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val searchPhotosUseCase: SearchPhotosUseCase,
    val dispatcher: DispatcherProvider
) : ViewModel() {

    private val TAG = "HomeViewModel"
    private val _photos = MutableStateFlow<List<PhotoDomainModel>>(listOf())
    val photos = _photos.asStateFlow()

    private val _serviceStarted = MutableStateFlow(false)
    val serviceStarted = _serviceStarted.asStateFlow()

    var startServiceOnPermissionGranted = true

    private var observePhotosJob: Job? = null

    fun homeUiState(): HomeUiState {
        return HomeUiState(
            photos = photos,
            serviceStarted = serviceStarted
        )
    }

    fun observeService() {
        if (serviceStarted.value) {
            observeApiPhotos()
        } else {
            stopObservingApiPhotos()
        }
    }

    private fun observeApiPhotos() {
        Timber.tag(TAG).i("observeApiPhotos")
        observePhotosJob = viewModelScope.launch(dispatcher.background()) {
            searchPhotosUseCase.photos().collect {
                _photos.value = photos.value.toMutableList().apply {
                    add(0, it) //add every new photo on top of the list
                    Timber.tag(TAG).i("collected: ${it.url}")
                }
            }
        }
    }

    private fun stopObservingApiPhotos() {
        Timber.tag(TAG).i("stopObservingApiPhotos")
        observePhotosJob?.cancel()
    }

    fun setServiceStatus(started: Boolean) {
        _serviceStarted.value = started
    }

}