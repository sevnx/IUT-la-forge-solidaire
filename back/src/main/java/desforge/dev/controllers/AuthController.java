package desforge.dev.controllers;

import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.services.IAuthService;
import desforge.dev.services.ICookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @Autowired
    private ICookieService cookieService;

    @Value("${cookie.name}")
    private String cookieName;

    @Value("${cookie.expiration}")
    private int cookieExpirationMs;


    /*
    * @param registerRequest
    * @return registerResponse
    */
    @PostMapping(value = "/register", produces = "application/json")
    public void registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        try {
            String token = authService.register(registerRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        } catch (RegisterException e) {
            throw new RegisterException("Register failed: " + e.getMessage());
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public void loginUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            String token = authService.login(loginRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        } catch (RegisterException e) {
            throw new RegisterException("login failed: " + e.getMessage());
        }
    }
}
