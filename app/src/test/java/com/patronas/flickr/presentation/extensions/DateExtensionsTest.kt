package com.patronas.flickr.presentation.extensions

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class DateExtensionsTest {

    @Test
    fun `given two dates with difference less than interval, verify intervalPassed() returns false`() {
        val oldDate = Calendar.getInstance()
        val newDate = Calendar.getInstance()
        newDate.timeInMillis = oldDate.timeInMillis + 3000

        assertFalse(newDate.intervalPassed(intervalInMilis = 5000, previousDate = oldDate))
    }

    @Test
    fun `given two dates with difference more or equal to interval, verify intervalPassed() returns true`() {
        val oldDate = Calendar.getInstance()
        val newDate = Calendar.getInstance()
        newDate.timeInMillis = oldDate.timeInMillis + 8000

        assertTrue(newDate.intervalPassed(intervalInMilis = 5000, previousDate = oldDate))
        assertTrue(newDate.intervalPassed(intervalInMilis = 8000, previousDate = oldDate))
    }

}