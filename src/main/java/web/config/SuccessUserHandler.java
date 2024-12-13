package web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl;

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            redirectUrl = "/admin";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            redirectUrl = "/user";
        } else {
            redirectUrl = "/api";
        }

        response.sendRedirect(redirectUrl);
    }
}
