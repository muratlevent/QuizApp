package com.example.QuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling the home page requests.
 * This controller is responsible for directing the user to the home page of the Quiz Game application.
 */
@Controller
public class HomeController {

    /**
     * Handles requests to the home page of the Quiz Game.
     *
     * @return The name of the view for the home page.
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
