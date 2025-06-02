package desforge.dev.back;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parseResponseBody(Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            return objectMapper.createObjectNode();
        }
        return objectMapper.readTree(body.string());
    }

    public static String getErrorMessage(Response response) throws IOException {
        JsonNode responseBody = parseResponseBody(response);
        JsonNode errorNode = responseBody.get("error");
        return errorNode != null ? errorNode.asText() : null;
    }

    public static boolean hasValidPasswordStructure(String password) {
        if (password.length() < 12) return false;
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> 
            "!@#$%^&*()_+-={}[]:;\"'<>,.?/\\|~`".indexOf(c) >= 0);
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static Map<String, Object> createRegisterPayload(String login, String password, String address) {
        return Map.of(
                "login", login,
                "password", password,
                "address", address
        );
    }

    public static Map<String, Object> createLoginPayload(String login, String password) {
        return Map.of(
                "login", login,
                "password", password
        );
    }

    public static String generateUniqueLogin() {
        return "testuser_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }

    public static List<String> generateBatchPasswords(int count) {
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> generateValidPassword())
                .toList();
    }

    private static String generateValidPassword() {
        return "TestPass123!";
    }
} 