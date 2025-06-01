package desforge.dev.security;

import desforge.dev.repositories.UserRepository;
import desforge.dev.services.ITokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private ITokenService jwtService;

    @Autowired
    UserRepository userRepository;

    @Value("${cookie.name}")
    private String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (token != null && jwtService.validateJwtToken(token)) {
            String login = jwtService.getUsernameFromToken(token);
            userRepository.findByUsername(login).ifPresent(user -> {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }

        filterChain.doFilter(request, response);
    }
}
