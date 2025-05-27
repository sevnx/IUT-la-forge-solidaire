package desforge.dev.services;


import desforge.dev.entities.User;
import desforge.dev.errors.LoginException;
import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;
import desforge.dev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService implements IAuthService {

    @Autowired
    private ITokenService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICookieService cookieService;

    @Value("${cookie.name}")
    private String cookieName;

    @Value("${cookie.expiration}")
    private int cookieExpirationMs;

    /*
    * @param request the login model
    * @return the token
    */
    public String login(LoginRequest request) {
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
            userRepository.save(user);

            return jwtService.generateToken(user.getUsername());
        } else {
            throw new RegisterException("Username already exists");
        }
    }
}
