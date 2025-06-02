package desforge.dev.back.tests.auth;

import desforge.dev.back.BaseIntegrationTest;
import desforge.dev.back.CookieTestConfiguration;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Import(CookieTestConfiguration.class)
class AuthTest extends BaseIntegrationTest {

    @Test
    void shouldRegisterAndLoginUser() throws IOException {
        TestUser user = createTestUser();
        
        try (Response registerResponse = registerUser(user)) {
            assertEquals(200, registerResponse.code());
            assertTrue(isUserLoggedIn());
        }
        
        clearDefaultCookies();
        assertFalse(isUserLoggedIn());
        
        try (Response loginResponse = loginUser(user)) {
            assertEquals(200, loginResponse.code());
            assertTrue(isUserLoggedIn());
        }
    }

    @Test
    void shouldHandleMultipleClientsIndependently() throws IOException {
        TestUser user1 = createTestUser("user1");
        TestUser user2 = createTestUser("user2");
        
        String client1 = "client1";
        String client2 = "client2";
        
        try (Response registerResponse1 = registerUser(user1, client1)) {
            assertEquals(200, registerResponse1.code());
        }
        
        try (Response registerResponse2 = registerUser(user2, client2)) {
            assertEquals(200, registerResponse2.code());
        }
        
        assertTrue(isUserLoggedIn(client1));
        assertTrue(isUserLoggedIn(client2));
        
        clearCookies(client1);
        assertFalse(isUserLoggedIn(client1));
        assertTrue(isUserLoggedIn(client2));
    }

    @Test
    void shouldRejectInvalidRegistration() throws IOException {
        TestUser userWithShortPassword = new TestUser("testuser", "short", "123 Test St");
        
        try (Response response = registerUser(userWithShortPassword)) {
            assertEquals(400, response.code());
            assertFalse(isUserLoggedIn());
        }
    }

    @Test
    void shouldRejectDuplicateRegistration() throws IOException {
        TestUser user = createTestUser();
        
        try (Response firstResponse = registerUser(user)) {
            assertEquals(200, firstResponse.code());
        }
        
        clearDefaultCookies();
        
        try (Response duplicateResponse = registerUser(user)) {
            assertEquals(409, duplicateResponse.code());
        }
    }

    @Test
    void shouldRejectInvalidLogin() throws IOException {
        TestUser nonExistentUser = createTestUser();
        
        try (Response response = loginUser(nonExistentUser)) {
            assertEquals(401, response.code());
            assertFalse(isUserLoggedIn());
        }
    }

    @Test
    void shouldMaintainSessionAcrossRequests() throws IOException {
        TestUser user = createTestUser();
        
        try (Response registerResponse = registerUser(user)) {
            assertEquals(200, registerResponse.code());
        }
        
        assertTrue(isUserLoggedIn());
        assertTrue(isUserLoggedIn());
        assertTrue(isUserLoggedIn());
    }
} 