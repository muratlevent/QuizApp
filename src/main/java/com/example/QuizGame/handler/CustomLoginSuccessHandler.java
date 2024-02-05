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

@Component("CustomLoginSuccessHandler")
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String username = authentication.getName();
        session.setAttribute("username", username);

        // This block is for getting the user ID from User and storing user ID in session
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = (User) userDetails;
            Long userId = user.getId();
            session.setAttribute("userId", userId);
        }
        response.sendRedirect("/start");
    }
}
