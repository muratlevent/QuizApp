package com.example.QuizGame.controller;

import com.example.QuizGame.model.User;
import com.example.QuizGame.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

/**
 * Controller for handling registration requests for the Quiz Game application.
 * This controller manages user registration, including displaying the registration form and processing user registration.
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;

    /**
     * Shows the registration form to the user.
     *
     * @return A ModelAndView object containing the view name and a new user object for the form.
     */
    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    /**
     * Processes the user registration.
     * This method handles the registration logic including saving the new user to the database, validating user input,
     * encoding the password, and authenticating the user upon successful registration.
     *
     * @param newUser The user object populated from the registration form.
     * @param result BindingResult that holds the result of the validation and binding and contains errors that may have occurred.
     * @return Redirects to the start page on successful registration, or back to the registration form on error.
     */
    @Transactional
    @PostMapping("/register")
    public String registerUser(@Valid User newUser, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        String originalPassword = newUser.getPassword();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setActive(true);
        userRepository.save(newUser);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(newUser.getUsername(), originalPassword)
        );

        if (auth.isAuthenticated()) {
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
            session.setAttribute("username", newUser.getUsername());
            session.setAttribute("userId", newUser.getId());


            return "redirect:/start";
        } else {
            return "redirect:/login";
        }
    }
}
