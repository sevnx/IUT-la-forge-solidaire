package desforge.dev.services;

import javax.crypto.SecretKey;

public interface ITokenService {

    public String generateToken(String subject, String audience,
                                             SecretKey secretKey);
    public String getUsernameFromToken(String token, SecretKey secretKey);
    public boolean validateJwtToken(String token, SecretKey secretKey);
    public SecretKey generateSecretKey();
}
