package com.patronas.flickr.presentation.extensions

import java.util.*


fun Calendar.intervalPassed(intervalInMilis: Long, previousDate: Calendar): Boolean {
    val difference: Long = this.timeInMillis - previousDate.timeInMillis
    return difference >= intervalInMilis
}