package com.patronas.flickr.presentation.ui.screens.screens.home

import com.patronas.domain.model.PhotoDomainModel
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val photos: StateFlow<List<PhotoDomainModel>>,
    val serviceStarted: StateFlow<Boolean>
)