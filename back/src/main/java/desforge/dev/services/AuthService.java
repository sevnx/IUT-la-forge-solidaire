package desforge.dev.services;

import java.util.UUID;

import desforge.dev.entities.User;
import desforge.dev.errors.LoginException;
import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private ITokenService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    * @param request the login model
    * @return the token
    */
    public String login(LoginRequest request) {
        String login = request.getLogin();
        User user = userRepository.findByUsername(login);

        if (user == null) {
            throw new LoginException("Invalid username or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginException("Invalid username or password");
        }

        SecretKey secretKey = jwtService.generateSecretKey();
        String audience = UUID.randomUUID().toString();
        return jwtService.generateToken(login, audience, secretKey);
    }

    /*
     *
     * @param request the register model
     * @return the token
     */
    public String register(RegisterRequest registerRequest) throws RegisterException {
        if (!userRepository.existsByUsername(registerRequest.getLogin())){
            User user = new User();
            user.setAddress(registerRequest.getAddress());
            user.setUsername(registerRequest.getLogin());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            String audience = UUID.randomUUID().toString();

            SecretKey secretKey = jwtService.generateSecretKey();

            String token = jwtService.generateToken(user.getUsername(), audience, secretKey);
            userRepository.save(user);
            return token;
        } else {
            throw new RegisterException("Username already exists");
        }
    }
}
