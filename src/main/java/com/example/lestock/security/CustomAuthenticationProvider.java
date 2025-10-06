package com.example.lestock.security;
import com.example.lestock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String providedPassword = authentication.getCredentials().toString();

        return userService.findByUserName(username)
                .map(user -> {
                    boolean matches = passwordEncoder.matches(providedPassword, user.getPassword());
                    if (matches) {
                        return new CustomAuthentication(user);
                    }
                    else  {
                        throw new UsernameNotFoundException("Invalid username and/or password!");
                    }
                }).orElseThrow(() -> new UsernameNotFoundException("Invalid username and/or password!"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
