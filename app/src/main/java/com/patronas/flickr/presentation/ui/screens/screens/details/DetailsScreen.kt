package com.patronas.flickr.presentation.ui.screens.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.patronas.domain.model.PhotoDomainModel
import com.patronas.flickr.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsScreen(photo: PhotoDomainModel) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = photo.title)
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