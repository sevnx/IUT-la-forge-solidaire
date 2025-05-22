package desforge.dev.controllers;

import desforge.dev.entities.User;
import desforge.dev.errors.RegisterException;
import desforge.dev.modals.auth.RegisterRequest;
import desforge.dev.modals.auth.RegisterResponse;
import desforge.dev.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    /*
    * @param registerRequest
    * @return registerResponse
    */
    @PostMapping(value = "/register", produces = "application/json")
    public RegisterResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = new RegisterResponse();
        try {
            response.setToken(authService.register(registerRequest));
            return response;
        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public User loginUser(@RequestBody User user) {
        return user;
    }
}
