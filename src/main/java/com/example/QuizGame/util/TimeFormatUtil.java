package com.example.QuizGame.util;

/**
 * Utility class for formatting time.
 * This class provides a method for converting time in seconds into a human-readable format.
 */
public class TimeFormatUtil {

    /**
     * Converts a total number of seconds into a human-readable time format.
     * The format will be adjusted depending on whether the time includes minutes or is only seconds.
     * For example, for 90 seconds, it will return "1 min : 30 sec".
     *
     * @param totalSeconds The total number of seconds to be formatted.
     * @return A string representing the formatted time in minutes and seconds.
     */
    public static String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        if (minutes > 0 && seconds > 0) {
            return String.format("%d min : %d sec", minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d min", minutes);
        } else {
            return String.format("%d sec", seconds);
        }
    }
}
