package desforge.dev.services;

import desforge.dev.entities.User;
import desforge.dev.errors.RegisterException;
import desforge.dev.modals.auth.RegisterRequest;
import desforge.dev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ITokenService jwtService;

    /*
    * @param registerRequest the register modal
    * @return token
    */
    public String register(RegisterRequest registerRequest) throws RegisterException {
        if (!userRepository.existsByUsername(registerRequest.getLogin())){
            User user = new User();
            user.setAddress(registerRequest.getAddress());
            user.setUsername(registerRequest.getLogin());
            user.setPassword(registerRequest.getPassword());
            String token = jwtService.generateToken(user.getUsername());
            userRepository.save(user);
            return token;
        } else {
            throw new RegisterException("Username already exists");
        }

    }
}
