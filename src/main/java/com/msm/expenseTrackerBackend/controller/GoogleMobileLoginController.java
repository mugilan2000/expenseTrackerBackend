package com.msm.expenseTrackerBackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

public class GoogleMobileLoginController  {

    @GetMapping("/api/auth/google-mobile")
    public void googleMobileLogin(
            HttpSession session,
            HttpServletResponse response) throws IOException {

        session.setAttribute("oauth_platform", "android");

        response.sendRedirect("/oauth2/authorization/google");
    }
}
