package desforge.dev.controllers;

import desforge.dev.errors.LoginException;
import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.repositories.UserRepository;
import desforge.dev.services.IAuthService;
import desforge.dev.services.ICookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    * The endpoint used to register a user.
    * @param registerRequest
    */
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        try {
            String token = authService.register(registerRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RegisterException e) {
            throw new RegisterException("Register failed: " + e.getMessage());
        }
    }

    /*
     * The endpoint used to log a user.
     * @param loginRequest
     */
    @PostMapping(value = "/login", produces = "application/json")
    public void loginUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            String token = authService.login(loginRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        } catch (LoginException e) {
            throw new LoginException("login failed: " + e.getMessage());
        }
    }

    /*
    * Log out a user
    * @param response
    */
    @PostMapping(value = "/logout")
    public void logoutUser(HttpServletResponse response) {
        ResponseCookie cookie = cookieService.removeCookie(cookieName);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
