package com.hahow.androidRecruitProject.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object DateUtil {

    private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val UTC_TIMEZONE = "UTC"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /**
     * 建立一個設定好 UTC 時區的 ISO 8601 SimpleDateFormat
     */
    fun createIsoFormat(): SimpleDateFormat {
        val format = SimpleDateFormat(ISO_FORMAT, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
        return format
    }

    /**
     * 從 ISO 8601 格式字串中提取日期部分
     * @param isoString ISO 8601 格式的日期字串，例如 "2023-10-01T12:00:00Z"
     * @return 日期字串，例如 "2023-10-01"，解析失敗時返回 null
     */
    fun extractDateFromIso(isoString: String?): String? {
        if (isoString.isNullOrBlank()) return null

        return try {
            val inputFormat = createIsoFormat()
            val date = inputFormat.parse(isoString) ?: return null

            val outputFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 計算兩個日期間的天數差（date1 - date2）
     * @param isoString1 第一個日期的 ISO 8601 格式字串
     * @param isoString2 第二個日期的 ISO 8601 格式字串
     * @return 天數差（date1 - date2，正負皆可）
     */
    fun daysBetween(isoString1: String, isoString2: String): Long {
        return try {
            val format = createIsoFormat()
            val date1 = format.parse(isoString1) ?: return 0L
            val date2 = format.parse(isoString2) ?: return 0L
            val diffInMillis = date1.time - date2.time
            TimeUnit.MILLISECONDS.toDays(diffInMillis)
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * 計算現在到目標日期的天數差
     * @param isoString 目標日期的 ISO 8601 格式字串
     * @return 天數差
     */
    fun daysFromNow(isoString: String): Long {
        val nowIso = createIsoFormat().format(Date())
        return daysBetween(isoString, nowIso)
    }

}