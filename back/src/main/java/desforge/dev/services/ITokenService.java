package desforge.dev.services;


public interface ITokenService {

    String generateToken(String subject);
    String getUsernameFromToken(String token);
    boolean validateJwtToken(String token);
}
