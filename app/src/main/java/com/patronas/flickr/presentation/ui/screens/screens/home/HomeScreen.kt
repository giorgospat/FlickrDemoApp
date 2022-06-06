package com.patronas.flickr.presentation.ui.screens.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patronas.domain.model.PhotoDomainModel
import com.patronas.flickr.R
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    startService: () -> Unit,
    navigateToDetails: (String) -> Unit
) {

    val uiState = viewModel.homeUiState()

    val serviceStarted = uiState.serviceStarted.collectAsState().value
    val photos = uiState.photos.collectAsState().value

    LaunchedEffect(serviceStarted) {
        viewModel.observeService()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        StartStopButton(
            isStarted = serviceStarted,
            onClick = {
                startService()
            }
        )
        ImagesList(photos = photos, onClick = { navigateToDetails(it) })
    }

}

@Composable
fun StartStopButton(
    isStarted: Boolean,
    onClick: () -> Unit
) {

    val buttonText = stringResource(id = R.string.stop_button_label).takeIf { isStarted }
        ?: stringResource(
            id = R.string.start_button_label
        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconToggleButton(
            checked = isStarted,
            onCheckedChange = { onClick() }
        ) {
            Text(text = buttonText)

        }
    }
}

@Composable
fun ImagesList(photos: List<PhotoDomainModel>, onClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(photos) { photo ->
            Box(modifier = Modifier
                .padding(vertical = 5.dp)
                .clickable {
                    onClick(photo.id)
                }) {
                photo.url?.let { url ->
                    GlideImage(
                        imageModel = url,
                        contentScale = ContentScale.Crop,
                        placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                        error = ImageBitmap.imageResource(R.drawable.placeholder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }
    }
}