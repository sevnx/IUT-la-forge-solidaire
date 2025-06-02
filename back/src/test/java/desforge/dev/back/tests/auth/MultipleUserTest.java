package desforge.dev.back.tests.auth;

import desforge.dev.back.BaseIntegrationTest;
import desforge.dev.back.TestUtils;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MultipleUserTest extends BaseIntegrationTest {

    @Test
    void shouldAccessProtectedEndpointsAfterLogin() throws IOException {
        TestUser user = createTestUser();
        
        try (Response registerResponse = registerUser(user)) {
            assertEquals(200, registerResponse.code());
        }
        
        try (Response userResponse = get("/user")) {
            assertEquals(200, userResponse.code());
        }
        
        try (Response toolsResponse = get("/user/tools")) {
            assertEquals(200, toolsResponse.code());
        }
        
        try (Response borrowsResponse = get("/user/borrows")) {
            assertEquals(200, borrowsResponse.code());
        }
    }

    @Test
    void shouldRejectUnauthenticatedAccessToProtectedEndpoints() throws IOException {
        try (Response userResponse = get("/user")) {
            assertEquals(401, userResponse.code());
        }
        
        try (Response toolsResponse = get("/user/tools")) {
            assertEquals(401, toolsResponse.code());
        }
    }

    @Test
    void shouldHandleConcurrentUsers() throws IOException {
        TestUser user1 = createTestUser("concurrent1");
        TestUser user2 = createTestUser("concurrent2");
        
        String client1 = "concurrent_client1";
        String client2 = "concurrent_client2";
        
        try (Response register1 = registerUser(user1, client1);
             Response register2 = registerUser(user2, client2)) {
            
            assertEquals(200, register1.code());
            assertEquals(200, register2.code());
        }
        
        try (Response user1Check = get("/user", client1);
             Response user2Check = get("/user", client2)) {
            
            assertEquals(200, user1Check.code());
            assertEquals(200, user2Check.code());
        }
        
        clearCookies(client1);
        
        try (Response user1Denied = get("/user", client1);
             Response user2StillOk = get("/user", client2)) {
            
            assertEquals(401, user1Denied.code());
            assertEquals(200, user2StillOk.code());
        }
    }

    @Test
    void shouldValidatePasswordPolicy() throws IOException {
        String validPassword = getValidPassword();
        Assertions.assertTrue(TestUtils.hasValidPasswordStructure(validPassword));
        
        String[] invalidPasswords = {
            "short",
            "nouppercase123!",
            "NOLOWERCASE123!",
            "NoSpecialChars123",
            "NoDigitsHere!"
        };
        
        for (String invalidPassword : invalidPasswords) {
            assertFalse(TestUtils.hasValidPasswordStructure(invalidPassword), 
                "Password should be invalid: " + invalidPassword);
        }
    }

    @Test
    void shouldMaintainSessionStateCorrectly() throws IOException {
        TestUser user = createTestUser();
        String clientName = "session_test";
        
        assertFalse(isUserLoggedIn(clientName));
        
        try (Response registerResponse = registerUser(user, clientName)) {
            assertEquals(200, registerResponse.code());
        }
        
        assertTrue(isUserLoggedIn(clientName));
        
        for (int i = 0; i < 5; i++) {
            assertTrue(isUserLoggedIn(clientName), 
                "Session should persist across multiple requests (attempt " + i + ")");
        }
        
        clearCookies(clientName);
        assertFalse(isUserLoggedIn(clientName));
    }

    @Test
    void shouldSupportMultipleNamedClients() throws IOException {
        String[] clientNames = {"admin", "user1", "user2", "guest"};
        TestUser[] users = new TestUser[clientNames.length];
        
        for (int i = 0; i < clientNames.length; i++) {
            users[i] = createTestUser(clientNames[i]);
            try (Response response = registerUser(users[i], clientNames[i])) {
                assertEquals(200, response.code());
            }
        }
        
        for (String clientName : clientNames) {
            assertTrue(isUserLoggedIn(clientName), 
                "Client " + clientName + " should be logged in");
        }
        
        clearCookies(clientNames[0]);
        assertFalse(isUserLoggedIn(clientNames[0]));
        
        for (int i = 1; i < clientNames.length; i++) {
            assertTrue(isUserLoggedIn(clientNames[i]), 
                "Client " + clientNames[i] + " should still be logged in");
        }
    }
} 