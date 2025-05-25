package desforge.dev.services;

import desforge.dev.errors.RegisterException;
import desforge.dev.models.auth.LoginRequest;
import desforge.dev.models.auth.RegisterRequest;

public interface IAuthService {

    public String login(LoginRequest request) ;

    public String register(RegisterRequest registerRequest) throws RegisterException;
}
