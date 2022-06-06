package com.patronas.flickr.presentation.ui.screens.main_activity.navigation

import androidx.navigation.NavController
import timber.log.Timber

fun NavController.navigateToScreen(
    screen: String
) {

    try {
        this.navigate(screen) {
            launchSingleTop = true
        }
    } catch (e: Exception) {
        Timber.tag(NAVIGATION_TAG).e(e.toString())
    }

}

fun NavController.navigateToScreenWithParam(
    screen: String,
    param: String
) {

    try {
        val route = "$screen/$param"
        this.navigate(route) {
            launchSingleTop = true
        }
    } catch (e: Exception) {
        Timber.tag(NAVIGATION_TAG).e(e.toString())
    }

}

const val NAVIGATION_TAG = "navigationExtensions"