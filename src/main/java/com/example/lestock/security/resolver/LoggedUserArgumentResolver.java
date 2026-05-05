package com.example.lestock.security.resolver;

import com.example.lestock.model.User;
import com.example.lestock.security.CustomUserDetails;
import com.example.lestock.security.CustomUserDetailsService;
import com.example.lestock.security.annotation.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoggedUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final CustomUserDetailsService userDetailsService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new AuthenticationCredentialsNotFoundException("Token is missing");
        }

        jwt = (Jwt) authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername(jwt.getSubject());

        return userDetails.getDomainUser();
    }
}
