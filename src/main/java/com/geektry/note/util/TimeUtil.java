package com.geektry.note.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Chaohang Fu
 */
public class TimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final long SECONDS_PERIOD_OF_MINUTE = 60L;
    private static final long SECONDS_PERIOD_OF_HOUR = SECONDS_PERIOD_OF_MINUTE * 60L;
    private static final long SECONDS_PERIOD_OF_DAY = SECONDS_PERIOD_OF_HOUR * 24L;
    private static final long SECONDS_PERIOD_OF_3DAYS = SECONDS_PERIOD_OF_DAY * 3L;

    public static String beautify(LocalDateTime before) {

        if (before == null) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        long timestampOfNow = now.toEpochSecond(ZoneOffset.UTC);
        long timestampOfBefore = before.toEpochSecond(ZoneOffset.UTC);
        long secondsPeriod = timestampOfNow - timestampOfBefore;

        if (secondsPeriod < SECONDS_PERIOD_OF_MINUTE) {
            return secondsPeriod + "秒前";
        }
        if (secondsPeriod < SECONDS_PERIOD_OF_HOUR) {
            return secondsPeriod / SECONDS_PERIOD_OF_MINUTE + "分钟前";
        }
        if (secondsPeriod < SECONDS_PERIOD_OF_DAY) {
            return secondsPeriod / SECONDS_PERIOD_OF_HOUR + "小时前";
        }
        if (secondsPeriod < SECONDS_PERIOD_OF_3DAYS) {
            return secondsPeriod / SECONDS_PERIOD_OF_DAY + "天前";
        }
        return before.format(FORMATTER);
    }
}
