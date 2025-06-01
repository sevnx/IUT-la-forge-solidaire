package desforge.dev.services;


import desforge.dev.entities.User;
import desforge.dev.errors.auth.LoginException;
import desforge.dev.errors.auth.RegisterException;
import desforge.dev.models.auth.Login;
import desforge.dev.models.auth.Register;
import desforge.dev.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService implements IAuthService {

    private final int MIN_PASSWORD_LENGTH = 12;
    private final int MIN_LOGIN_LENGTH = 2;
    private final int MAX_LOGIN_LENGTH = 32;
    private final String REQUIRED_PASSWORD_CHARS = "!@#$%^&*()_+-={}[]:;\"'<>,.?/\\|~`";
    @Autowired
    private ITokenService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(Login request) {
        String login = request.getLogin();
        Optional<User> userOpt = userRepository.findByUsername(login);

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            throw new LoginException("Invalid username or password");
        }
        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginException("Invalid username or password");
        }
        return jwtService.generateToken(user.getUsername());
    }

    public String register(@Valid Register registerRequest) throws RegisterException, IllegalArgumentException {
        String login = registerRequest.getLogin();
        String password = registerRequest.getPassword();
        if (!userRepository.existsByUsername(registerRequest.getLogin())) {
            if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
                throw new IllegalArgumentException("The login length must be between " + MIN_LOGIN_LENGTH + " and " + MAX_LOGIN_LENGTH);
            }
            validatePassword(password);

            User user = new User();
            user.setAddress(registerRequest.getAddress());
            user.setUsername(registerRequest.getLogin());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userRepository.save(user);

            return jwtService.generateToken(user.getUsername());
        } else {
            throw new RegisterException("Username already exists");
        }
    }

    private void validatePassword(String password) throws IllegalArgumentException {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("The password length must be at least " + MIN_PASSWORD_LENGTH);
        }

        boolean hasSpecial = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (REQUIRED_PASSWORD_CHARS.indexOf(c) >= 0) hasSpecial = true;
            if (Character.isUpperCase(c)) hasUppercase = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        if (!hasSpecial) throw new IllegalArgumentException("The password must contain a special character");
        if (!hasUppercase) throw new IllegalArgumentException("The password must contain an uppercase letter");
        if (!hasDigit) throw new IllegalArgumentException("The password must contain a digit");
    }

}
