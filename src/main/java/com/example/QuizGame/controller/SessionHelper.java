package com.example.QuizGame.controller;

import jakarta.servlet.http.HttpSession;

public class SessionHelper {

    private final HttpSession session;

    public SessionHelper(HttpSession session) {
        this.session = session;
    }

    public void removeAttributes(String... attributes) {
        for (String attr : attributes) {
            session.removeAttribute(attr);
        }
    }

    public void setAttribute(String key, Object value) {
        session.setAttribute(key, value);
    }

    public <T> T getAttribute(String key, Class<T> type) {
        return type.cast(session.getAttribute(key));
    }
}
