package com.example.QuizGame.controller;

import jakarta.servlet.http.HttpSession;

/**
 * Helper class to manage session attributes for the quiz game.
 * Provides convenience methods to set, get, and remove session attributes,
 * as well as initializing quiz state and selecting categories.
 */
public class SessionHelper {

    private final HttpSession session;

    /**
     * Constructs a SessionHelper with the given HTTP session.
     *
     * @param session The HTTP session object to be managed.
     */
    public SessionHelper(HttpSession session) {
        this.session = session;
    }

    /**
     * Removes specified attributes from the session.
     *
     * @param attributes The names of the attributes to be removed.
     */
    public void removeAttributes(String... attributes) {
        for (String attr : attributes) {
            session.removeAttribute(attr);
        }
    }

    /**
     * Sets an attribute in the session.
     *
     * @param key The name of the attribute to be set.
     * @param value The value of the attribute to be set.
     */
    public void setAttribute(String key, Object value) {
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session and casts it to the specified type.
     *
     * @param key The name of the session attribute to retrieve.
     * @param type The class of the type to cast the attribute value to.
     * @return The attribute value cast to the specified type.
     */
    public <T> T getAttribute(String key, Class<T> type) {
        return type.cast(session.getAttribute(key));
    }

    /**
     * Sets the selected quiz category in the session.
     *
     * @param category The category to set as the selected quiz category.
     */
    public void selectCategory(String category) {
        session.setAttribute("selectedCategory", category);
    }

    /**
     * Initializes the quiz by resetting scores, time taken,
     * and removing any previous quiz state from the session.
     */
    public void initializeQuiz() {
        setAttribute("gameScore", 0);
        setAttribute("timeTaken", 0);
        removeAttributes("randomQuestions", "currentQuestionIndex", "actionState", "selectedCategory");
    }
}
