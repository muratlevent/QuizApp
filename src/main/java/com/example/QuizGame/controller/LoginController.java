package com.example.QuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling login page requests.
 * This controller manages directing users to the login page of the Quiz Game application.
 */
@Controller
public class LoginController {

    /**
     * Handles requests to the login page.
     * This method returns the view name for the login page where users can enter their credentials.
     *
     * @return The name of the view for the login page.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
