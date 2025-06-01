package desforge.dev.services;

import desforge.dev.errors.auth.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;

public interface IAuthService {

    String login(LoginRequest request);

    String register(RegisterRequest registerRequest) throws RegisterException, IllegalArgumentException;
}
