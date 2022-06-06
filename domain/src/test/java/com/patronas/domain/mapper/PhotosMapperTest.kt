package com.patronas.domain.mapper

import com.patronas.domain.model.PhotoDomainModel
import com.patronas.network.model.Photo
import com.patronas.network.model.Photos
import com.patronas.network.model.SearchPhotosApiResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotosMapperTest {

    private val mapper = PhotosMapper()

    @Test
    fun `given photo api response, verify mapper returns correct photo url`() {
        val dummyApiResponse = SearchPhotosApiResponse(
            photos = Photos(
                photos = listOf(
                    Photo(
                        photoId = "51944797883",
                        secret = "6426d4377e",
                        server = "65535",
                        title = "Statue"
                    )
                )
            )
        )

        val exceptedModel = PhotoDomainModel(
            id = "51944797883",
            url = "https://live.staticflickr.com/65535/51944797883_6426d4377e_z.jpg",
            title = "Statue"
        )

        assertEquals(mapper.mapToDomain(dummyApiResponse), exceptedModel)

    }

    @Test
    fun `given photo api response with null properties, verify mapper returns null photo url`() {
        val dummyApiResponse = SearchPhotosApiResponse(
            photos = Photos(
                photos = listOf(
                    Photo(
                        photoId = null,
                        secret = "6426d4377e",
                        server = null,
                        title = "Statue"
                    )
                )
            )
        )

        val exceptedUrl = PhotoDomainModel(
            url = null,
            title = "Statue"
        )

        assertEquals(mapper.mapToDomain(dummyApiResponse), exceptedUrl)

    }

}