package com.msm.expenseTrackerBackend.util;

import com.msm.expenseTrackerBackend.model.User;
import com.msm.expenseTrackerBackend.service.OAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2UserService oauth2UserService;
    private JwtTokenProvider jwtTokenProvider;

    @Value("${frontend.url}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(
            OAuth2UserService oauth2UserService) {

        this.oauth2UserService = oauth2UserService;
        this.jwtTokenProvider = new JwtTokenProvider();
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        User user =
                oauth2UserService.processUser(oauthUser);

        System.out.println(
                "Google login successful: "
                        + user.getEmail()
        );

        String platform = request.getParameter("platform");
        System.out.println("Platform: " + platform);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        if ("android".equals(platform)) {

            response.sendRedirect(
                    "com.exptracker.app://oauth2redirect"
                            + "?token=" + token  + "&username=" + user.getUsername()

            );

        }else{
            response.sendRedirect(
                    frontendUrl + "/oauth-success?token=" + token + "&username=" + user.getUsername()
            );
        }

    }
}
