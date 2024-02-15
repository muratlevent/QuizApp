package com.example.QuizGame.controller;

import jakarta.servlet.http.HttpSession;

public class CategorySelectionHelper {

    private final HttpSession session;

    public CategorySelectionHelper(HttpSession session) {
        this.session = session;
    }

    public void selectCategory(String category) {
        session.setAttribute("selectedCategory", category);
    }
}
