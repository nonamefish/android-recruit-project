package com.hahow.androidRecruitProject.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateUtil {

    private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val UTC_TIMEZONE = "UTC"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /**
     * 從 ISO 8601 格式字串中提取日期部分
     * @param isoString ISO 8601 格式的日期字串，例如 "2023-10-01T12:00:00Z"
     * @return 日期字串，例如 "2023-10-01"，解析失敗時返回 null
     */
    fun extractDateFromIso(isoString: String?): String? {
        if (isoString.isNullOrBlank()) return null

        return try {
            val inputFormat = SimpleDateFormat(ISO_FORMAT, Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
            val date = inputFormat.parse(isoString) ?: return null

            val outputFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            null
        }
    }
}