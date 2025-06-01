package desforge.dev.services;

import desforge.dev.errors.auth.RegisterException;
import desforge.dev.models.auth.Login;
import desforge.dev.models.auth.Register;

public interface IAuthService {

    String login(Login request);

    String register(Register registerRequest) throws RegisterException, IllegalArgumentException;
}
