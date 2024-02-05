package com.example.QuizGame.dto;

import java.sql.Timestamp;

public class UserScoreDTO {
    private String username;
    private Integer score;
    private Integer timeTaken;
    private Timestamp date;

    public UserScoreDTO(String username, Integer score, Integer timeTaken, Timestamp date) {
        this.username = username;
        this.score = score;
        this.timeTaken = timeTaken;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
