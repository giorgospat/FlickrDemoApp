package com.patronas.flickr.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.patronas.domain.usecase.SearchPhotosUseCase
import com.patronas.flickr.R
import com.patronas.flickr.presentation.extensions.intervalPassed
import com.patronas.flickr.presentation.ui.screens.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LocationUpdateService : Service() {

    @Inject
    lateinit var searchPhotosUseCase: SearchPhotosUseCase

    private val TAG = "LocationService"
    private val notificationId = 1
    private val notificationChannelId = "com.patronas.flickr.demo"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var lastLocationUpdate: Calendar? = null
    private var minLocationUpdateInterval: Long =
        10 * 1000 // read location updates more than 10 second difference


    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            val newLocation = result.lastLocation
            Timber.tag(TAG).i(newLocation.toString())

            val now = Calendar.getInstance()
            if (shouldRequestImage(currentTime = now)) {
                lastLocationUpdate = now
                scope.launch {
                    searchPhotosUseCase.searchPhotos(
                        lat = newLocation.latitude,
                        lon = newLocation.longitude
                    )
                }
                Timber.tag(TAG).i("request new Image: TRUE")
            } else {
                Timber.tag(TAG).i("request new Image: FALSE")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        displayForegroundServiceNotification()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        LocationHelper().requestLocationUpdates(fusedLocationClient, locationCallback)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun displayForegroundServiceNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(
                    this,
                    0,
                    intentMainLanding,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            val iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            val mNotificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(
                        notificationChannelId, "Image Notifications",
                        NotificationManager.IMPORTANCE_MIN
                    )
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, notificationChannelId)

            builder.setContentTitle((resources.getString(R.string.app_name)))
                .setTicker(resources.getString(R.string.app_name))
                .setContentText(resources.getString(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
            iconNotification?.let {
                builder.setLargeIcon(Bitmap.createScaledBitmap(it, 128, 128, false))
            }
            val notification = builder.build()
            startForeground(notificationId, notification)

        }
    }

    private fun shouldRequestImage(currentTime: Calendar): Boolean {
        return lastLocationUpdate?.let {
            currentTime.intervalPassed(
                intervalInMilis = minLocationUpdateInterval,
                previousDate = it
            )
        } ?: true

    }
}


