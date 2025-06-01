package desforge.dev.controllers;

import desforge.dev.errors.auth.LoginException;
import desforge.dev.errors.auth.RegisterException;
import desforge.dev.models.auth.Login;
import desforge.dev.models.auth.Register;
import desforge.dev.models.error.ErrorResponse;
import desforge.dev.services.IAuthService;
import desforge.dev.services.ICookieService;
import desforge.dev.services.IErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @Autowired
    private ICookieService cookieService;

    @Autowired
    private IErrorService errorService;

    @Value("${cookie.name}")
    private String cookieName;

    @Value("${cookie.expiration}")
    private int cookieExpirationMs;

    @PostMapping(value = "/register", produces = "application/json")
    @Operation(
            summary = "User registration",
            description = "Creates a new user and returns a JWT token in a cookie.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    content = @Content(schema = @Schema(implementation = Register.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration successful (the token is sent in a cookie)."),
                    @ApiResponse(responseCode = "400", description = "The Login or the password is invalid.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "User already exists.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

            }
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody Register registerRequest, HttpServletResponse response) {
        try {
            String token = authService.register(registerRequest);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RegisterException e) {
            return errorService.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (IllegalArgumentException e) {
            return errorService.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PostMapping(value = "/login", produces = "application/json")
    @Operation(
            summary = "User login",
            description = "Logs in a user and returns a JWT token in a cookie.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login data",
                    content = @Content(schema = @Schema(implementation = Login.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful (the token is sent in a cookie)"),
                    @ApiResponse(responseCode = "401", description = "Invalid username or password",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> loginUser(@Valid @RequestBody Login login, HttpServletResponse response) {
        try {
            String token = authService.login(login);

            ResponseCookie cookie = cookieService.addCookie(cookieName, token, cookieExpirationMs);

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (LoginException e) {
            return errorService.buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @PostMapping(value = "/logout")
    @Operation(
            summary = "User logout",
            description = "Logs out a user by removing the JWT token cookie.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logout successful"),
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        ResponseCookie cookie = cookieService.removeCookie(cookieName);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
