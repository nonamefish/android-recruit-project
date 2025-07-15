package com.hahow.androidRecruitProject

import com.hahow.androidRecruitProject.util.DateUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DateUtilTest {
    @Test
    fun extractDateFromIsoReturnsCorrectDate() {
        // Given
        val iso = "2023-10-01T12:00:00Z"
        // When
        val date = DateUtil.extractDateFromIso(iso)
        // Then
        assertEquals("2023-10-01", date)
    }

    @Test
    fun extractDateFromIsoReturnsNullForInvalidInput() {
        // Given
        val invalidIso = "invalid"
        // When
        val date = DateUtil.extractDateFromIso(invalidIso)
        // Then
        assertNull(date)
    }

    @Test
    fun daysBetweenReturnsCorrectDifference() {
        // Given
        val d1 = "2023-10-03T00:00:00Z"
        val d2 = "2023-10-01T00:00:00Z"
        // When
        val diff = DateUtil.daysBetween(d1, d2)
        // Then
        assertEquals(2, diff)
    }

    @Test
    fun daysFromNowReturnsZeroForToday() {
        // Given
        val todayIso = DateUtil.createIsoFormat().format(java.util.Date())
        // When
        val diff = DateUtil.daysFromNow(todayIso)
        // Then
        assertEquals(0, diff)
    }
} 