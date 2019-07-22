package com.parveendala.newsapp.util;

import androidx.room.TypeConverter;

import java.util.Date;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
