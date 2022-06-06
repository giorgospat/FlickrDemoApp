package com.patronas.flickr.presentation.ui.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.patronas.flickr.R
import com.patronas.flickr.presentation.ui.BaseActivity
import com.patronas.flickr.presentation.ui.screens.main_activity.navigation.NavigationComponent
import com.patronas.flickr.presentation.ui.screens.screens.home.MainViewModel
import com.patronas.flickr.presentation.ui.theme.FlickrTheme
import com.patronas.flickr.service.LocationUpdateService

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val serviceStarted = mainViewModel.serviceStarted.collectAsState().value
            val navController = rememberNavController()

            FlickrTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationComponent(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        startService = {
                            when {
                                serviceStarted -> {
                                    stopLocationUpdateService()
                                }
                                else -> {
                                    startLocationUpdateService()
                                }
                            }
                        }
                    )
                }
            }
        }

    }

    private fun startLocationUpdateService() {
        if (locationPermissionEnabled()) {
            ContextCompat.startForegroundService(
                this,
                Intent(this, LocationUpdateService()::class.java)
            )
            mainViewModel.setServiceStatus(started = true)
        } else {
            mainViewModel.startServiceOnPermissionGranted = true
            requestLocationPermission()
        }
    }

    private fun stopLocationUpdateService() {
        mainViewModel.setServiceStatus(started = false)
        stopService(Intent(this, LocationUpdateService::class.java))
    }

    private fun requestLocationPermission() {
        if (!locationPermissionEnabled()) {
            permissionsResultCallback.launch(ACCESS_FINE_LOCATION)
        }
    }

    private fun locationPermissionEnabled(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(
                this,
                getString(R.string.denied_location_permission_message),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (mainViewModel.startServiceOnPermissionGranted) {
                startLocationUpdateService()
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlickrTheme {}
}