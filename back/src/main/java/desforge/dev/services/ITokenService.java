package desforge.dev.services;

public interface ITokenService {

    public String generateToken(String username);
    public String getUsernameFromToken(String token);
    public boolean validateJwtToken(String token);
}
