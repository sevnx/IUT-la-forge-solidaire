package desforge.dev.back;

import desforge.dev.services.ICookieService;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;

@TestConfiguration
@Profile("test")
public class CookieTestConfiguration {

    @Bean
    @Primary
    public ICookieService testCookieService() {
        return new ICookieService() {
            @Override
            public ResponseCookie addCookie(String name, String value, int expiration) {
                return ResponseCookie.from(name, value)
                        .httpOnly(true)
                        .path("/")
                        .secure(false)  // Disable secure for testing
                        .maxAge(expiration)
                        .sameSite(SameSiteCookies.LAX.toString())  // Use LAX instead of NONE for testing
                        .build();
            }

            @Override
            public ResponseCookie removeCookie(String name) {
                return ResponseCookie.from(name, null)
                        .httpOnly(true)
                        .path("/")
                        .secure(false)  // Disable secure for testing
                        .maxAge(0)
                        .sameSite(SameSiteCookies.LAX.toString())  // Use LAX instead of NONE for testing
                        .build();
            }
        };
    }
} 