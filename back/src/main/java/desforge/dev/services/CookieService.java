package desforge.dev.services;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService implements ICookieService {

    @Override
    public ResponseCookie addCookie(String name, String value, int expiration) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expiration)
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();
    }
}
