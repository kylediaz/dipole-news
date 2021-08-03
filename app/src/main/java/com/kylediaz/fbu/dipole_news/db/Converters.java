package com.kylediaz.fbu.dipole_news.db;

import androidx.room.TypeConverter;

import com.kylediaz.fbu.dipole_news.utils.StandardDateFormats;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

}
