package com.myprojects.FoodStore;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
@WebFilter
@Component
public class AuthenticationFilter implements Filter {


    @Autowired
    LoginSession loginSession;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isLoginPage(httpRequest)) {
            chain.doFilter(request, response);
        } else if (isUserLoggedIn(httpRequest)) {
            chain.doFilter(request, response);
        } else if (isRegisterPage(httpRequest)) {
            chain.doFilter(request, response);
        } else if (isHomePage(httpRequest)) {
            chain.doFilter(request, response);
        } else if (isUserListPage(httpRequest)) {
            chain.doFilter(request, response);
        }else {
            httpResponse.sendRedirect("/login");
        }
    }
    public boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") != null;
    }
    private boolean isLoginPage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/login");
    }
    private boolean isRegisterPage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/register");
    }

    private boolean isHomePage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/home");
    }

    private boolean isUserListPage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/allUsers");
    }
}