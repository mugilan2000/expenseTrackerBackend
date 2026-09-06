package com.msm.expenseTrackerBackend.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OAuthPlatformFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String platform = request.getParameter("platform");

        if ("android".equals(platform)) {
            request.getSession().setAttribute(
                    "oauth_platform",
                    "android"
            );
        }

        filterChain.doFilter(request, response);
    }
}
