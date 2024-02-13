package com.example.QuizGame.handler;

import com.example.QuizGame.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for successful authentication in the Quiz Game application.
 * This class implements the AuthenticationSuccessHandler interface to provide custom
 * logic after a user successfully authenticates.
 */
@Component("CustomLoginSuccessHandler")
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Handles the logic to be executed upon successful authentication.
     * This method sets the authenticated user's username and ID in the session and
     * redirects the user to the start page of the application.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param authentication The Authentication object containing details of the authenticated user.
     * @throws IOException If an input or output exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String username = authentication.getName();
        session.setAttribute("username", username);

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = (User) userDetails;
            Long userId = user.getId();
            session.setAttribute("userId", userId);
        }
        response.sendRedirect("/start");
    }
}
