package com.patronas.domain.mapper

import com.patronas.domain.model.PhotoDomainModel
import com.patronas.network.config.NetworkConstants.imageBaseUrl
import com.patronas.network.model.SearchPhotosApiResponse

class PhotosMapper {

    private val divider = "/"
    private val underscore = "_"
    private val fileExtension = ".jpg"
    private val size = "z"

    fun mapToDomain(apiResponse: SearchPhotosApiResponse): PhotoDomainModel {

        val photoApiModel = apiResponse.photos?.photos?.firstOrNull()

        return photoApiModel?.let {
            return PhotoDomainModel(
                id = it.photoId ?: "",
                url = constructImageUrl(
                    photoId = it.photoId,
                    serverId = it.server,
                    secret = it.secret
                ),
                title = it.title ?: ""
            )
        } ?: PhotoDomainModel()

    }

    private fun constructImageUrl(
        photoId: String?,
        serverId: String?,
        secret: String?
    ): String? {
        return if (photoId != null && serverId != null && secret != null) {
            imageBaseUrl + serverId + divider + photoId + underscore + secret + underscore + size + fileExtension
        } else {
            null
        }
    }

}