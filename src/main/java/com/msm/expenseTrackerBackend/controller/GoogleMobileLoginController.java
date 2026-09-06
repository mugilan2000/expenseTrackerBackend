package com.msm.expenseTrackerBackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GoogleMobileLoginController  {

    @GetMapping("/api/auth/google-mobile")
    public void googleMobileLogin(
            HttpSession session,
            HttpServletResponse response) throws IOException {

        System.out.println("Android Google login started");
        session.setAttribute("oauth_platform", "android");

        response.sendRedirect("/oauth2/authorization/google");
    }
}
