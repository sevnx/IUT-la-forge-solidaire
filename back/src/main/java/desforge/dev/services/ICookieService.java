package desforge.dev.services;

import org.springframework.http.ResponseCookie;

public interface ICookieService {
    ResponseCookie addCookie(String name, String value, int expiration);
}
