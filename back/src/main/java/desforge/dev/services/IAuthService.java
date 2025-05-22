package desforge.dev.services;

import desforge.dev.errors.RegisterException;
import desforge.dev.modals.auth.RegisterRequest;

public interface IAuthService {

    public String register(RegisterRequest registerRequest) throws RegisterException;

    //public String login(RegisterRequest registerRequest);
}
