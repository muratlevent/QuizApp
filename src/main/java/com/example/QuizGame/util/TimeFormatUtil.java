package com.example.QuizGame.util;

public class TimeFormatUtil {

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
