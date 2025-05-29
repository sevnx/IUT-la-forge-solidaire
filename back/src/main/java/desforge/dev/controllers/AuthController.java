package desforge.dev.controllers;

import desforge.dev.errors.LoginException;
import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.models.error.ErrorResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ErrorResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        try {
            String token = authService.register(registerRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RegisterException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
            return ResponseEntity.
                    status(HttpStatus.CONFLICT)
                    .body(errorResponse);
        }
    }

    /*
     * The endpoint used to log a user.
     * @param loginRequest
     */
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ErrorResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            String token = authService.login(loginRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (LoginException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse);
        }
    }

    /*
    * Log out a user
    * @param response
    */
    @PostMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    public void logoutUser(HttpServletResponse response, Authentication authentication) {
        ResponseCookie cookie = cookieService.removeCookie(cookieName);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
