package com.kylediaz.fbu.dipole_news.utils;

import java.text.SimpleDateFormat;

public class StandardDateFormats {

    private final static SimpleDateFormat ISO_DATE_TIME =
            new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'");

    public static SimpleDateFormat getISODateFormat() {
        return ISO_DATE_TIME;
    }

}
