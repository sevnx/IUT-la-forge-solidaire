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
                .path("/")
                .secure(true)
                .maxAge(expiration)
                .sameSite(SameSiteCookies.NONE.toString())
                .build();
    }

    @Override
    public ResponseCookie removeCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(0)
                .sameSite(SameSiteCookies.NONE.toString())
                .build();
    }
}
