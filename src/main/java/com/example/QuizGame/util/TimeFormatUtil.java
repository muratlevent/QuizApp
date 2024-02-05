package com.example.QuizGame.util;

public class TimeFormatUtil {

    public static String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d min : %d sec", minutes, seconds);
    }

}