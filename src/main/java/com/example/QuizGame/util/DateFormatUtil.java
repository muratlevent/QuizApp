package com.example.QuizGame.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class DateFormatUtil {

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
