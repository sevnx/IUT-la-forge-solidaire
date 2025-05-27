package desforge.dev.services;

import javax.crypto.SecretKey;

public interface ITokenService {

    public String generateToken(String subject);
    public String getUsernameFromToken(String token);
    public boolean validateJwtToken(String token);
}
