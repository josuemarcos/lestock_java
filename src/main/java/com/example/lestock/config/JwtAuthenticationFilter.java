package com.example.lestock.config;

import com.example.lestock.security.CustomUserDetailsService;
import com.example.lestock.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final static List<String> openPaths = List.of("/auth", "/health", "/error");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (isOpenPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");

        if (isAuthHeaderInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        final String informedUserName = jwtService.extractUsername(token);

        UserDetails informedUser = customUserDetailsService.loadUserByUsername(informedUserName);

        UsernamePasswordAuthenticationToken authToken = generateAuthToken(informedUser);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private static boolean isOpenPath(String path) {
        boolean pathIsOpen = false;
        for (String openPath : openPaths) {
            if (path.startsWith(openPath)) {
                pathIsOpen = true;
                break;
            }
        }
        return pathIsOpen;
    }

    private static boolean isAuthHeaderInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }


    private static UsernamePasswordAuthenticationToken generateAuthToken(UserDetails informedUser) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        informedUser,
                        null,
                        informedUser.getAuthorities()
                );
        return authToken;
    }
}
