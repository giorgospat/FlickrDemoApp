package com.patronas.network.config

object NetworkConstants {

    const val connectTimeoutSeconds: Long = 20
    const val readTimeoutSeconds: Long = 20
    const val writeTimeoutSeconds: Long = 20

    const val FLICKR_API_KEY = "d6e7c3649d51135a07edb2b1c2c61808"
    const val responseFormat = "json"
    const val imageBaseUrl = "https://live.staticflickr.com/"
    const val noJsonCallback = 1

    //Test config
    private const val FLICKR_URL_TEST = "https://api.flickr.com/services/"

    //Live config
    //placeholder for production url. For the needs of this assignment we use the same as test
    private const val FLICKR_URL_LIVE = "https://api.flickr.com/services/"

    fun getTestConfiguration(): NetworkConfiguration {
        return NetworkConfiguration(
            baseUrl = FLICKR_URL_TEST
        )
    }

    fun getLiveConfiguration(): NetworkConfiguration {
        return NetworkConfiguration(
            baseUrl = FLICKR_URL_LIVE
        )
    }

}