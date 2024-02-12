package com.example.QuizGame.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Utility class for formatting date and time.
 * This class provides methods for converting date and time into human-readable relative time formats.
 */
public class DateFormatUtil {

    /**
     * Converts a LocalDateTime object into a human-readable string representing how much time has passed since that date.
     * For example, it can return strings like "3 days ago", "1 hour ago", etc.
     *
     * @param pastDateTime The LocalDateTime object representing a past date and time.
     * @return A string representing the time elapsed since the past date and time, in a human-readable format.
     */
    public static String timeAgo(LocalDateTime pastDateTime) {
        LocalDateTime now = LocalDateTime.now();
        Period period = Period.between(pastDateTime.toLocalDate(), now.toLocalDate());
        Duration duration = Duration.between(pastDateTime.toLocalTime(), now.toLocalTime());

        if (period.getYears() > 0) return period.getYears() + " year" + (period.getYears() > 1 ? "s" : "") + " ago";
        if (period.getMonths() > 0) return period.getMonths() + " month" + (period.getMonths() > 1 ? "s" : "") + " ago";
        if (period.getDays() > 0) return period.getDays() + " day" + (period.getDays() > 1 ? "s" : "") + " ago";
        if (duration.toHours() > 0) return duration.toHours() + " hour" + (duration.toHours() > 1 ? "s" : "") + " ago";
        if (duration.toMinutes() > 0) return duration.toMinutes() + " minute" + (duration.toMinutes() > 1 ? "s" : "") + " ago";
        return "Just now";
    }
}
