package com.jadhavrupesh22.suhbat.utils

import android.app.Application
import android.content.Context

object GetTimeAgo : Application() {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = (24 * HOUR_MILLIS).toDouble()
    private const val WEEK_MILLIS = 7 * DAY_MILLIS
    private const val MONTH_MILLIS = DAY_MILLIS * 30
    private const val YEAR_MILLIS = WEEK_MILLIS * 52
    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            val roundup = (diff / MINUTE_MILLIS).toDouble()
            val b = roundup.toInt()
            "$b minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            val roundup = (diff / HOUR_MILLIS).toDouble()
            val b = roundup.toInt()
            "$b hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else if (diff < 7 * DAY_MILLIS) {
            val roundup = diff / DAY_MILLIS
            val b = roundup.toInt()
            "$b days ago"
        } else if (diff < 2 * WEEK_MILLIS) {
            "a week ago"
        } else if (diff < DAY_MILLIS * 30.43675) {
            val roundup = diff / WEEK_MILLIS
            val b = roundup.toInt()
            "$b weeks ago"
        } else if (diff < 2 * MONTH_MILLIS) {
            "a month ago"
        } else if (diff < WEEK_MILLIS * 52.2) {
            val roundup = diff / MONTH_MILLIS
            val b = roundup.toInt()
            "$b months ago"
        } else if (diff < 2 * YEAR_MILLIS) {
            "a year ago"
        } else {
            val roundup = diff / YEAR_MILLIS
            val b = roundup.toInt()
            "$b years ago"
        }
    }
}