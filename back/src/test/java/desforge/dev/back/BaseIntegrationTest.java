package desforge.dev.back;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    protected int port;

    protected String baseUrl;
    protected ObjectMapper objectMapper;
    private final Map<String, OkHttpClient> httpClients = new ConcurrentHashMap<>();
    private final Map<String, CookieManager> cookieManagers = new ConcurrentHashMap<>();
    protected static final String DEFAULT_CLIENT = "default";

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeEach
    void setUp() {
        baseUrl = "https://localhost:" + port + "/api";
        objectMapper = new ObjectMapper();
        initializeDefaultClient();
    }

    private void initializeDefaultClient() {
        getHttpClient(DEFAULT_CLIENT);
    }

    protected OkHttpClient getHttpClient(String clientName) {
        return httpClients.computeIfAbsent(clientName, name -> {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            cookieManagers.put(name, cookieManager);
            
            CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
            
            // Create a trust-all SSL context for testing
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                }
            };
            
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                
                return new OkHttpClient.Builder()
                        .cookieJar(cookieJar)
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier((hostname, session) -> true)
                        .build();
            } catch (Exception e) {
                // Fallback to regular HTTP client
                return new OkHttpClient.Builder()
                        .cookieJar(cookieJar)
                        .build();
            }
        });
    }

    protected OkHttpClient getDefaultClient() {
        return getHttpClient(DEFAULT_CLIENT);
    }

    protected Response post(String endpoint, Object body) throws IOException {
        return post(endpoint, body, DEFAULT_CLIENT);
    }

    protected Response post(String endpoint, Object body, String clientName) throws IOException {
        String json = objectMapper.writeValueAsString(body);
        System.out.println("POST " + baseUrl + endpoint);
        System.out.println("Request body: " + json);
        
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = getHttpClient(clientName).newCall(request).execute();
        
        System.out.println("Response code: " + response.code());
        if (response.body() != null) {
            String responseBody = response.body().string();
            System.out.println("Response body: " + responseBody);
            // Create new response with the body we just read
            return response.newBuilder()
                    .body(ResponseBody.create(responseBody, MediaType.get("application/json")))
                    .build();
        }
        
        return response;
    }

    protected Response get(String endpoint) throws IOException {
        return get(endpoint, DEFAULT_CLIENT);
    }

    protected Response get(String endpoint, String clientName) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .build();

        return getHttpClient(clientName).newCall(request).execute();
    }

    protected TestUser createTestUser() {
        String login = "testuser" + System.currentTimeMillis();
        String password = getValidPassword();
        String address = "123 Test Street, Test City";
        return new TestUser(login, password, address);
    }

    protected TestUser createTestUser(String suffix) {
        String login = "testuser" + suffix + System.currentTimeMillis();
        String password = getValidPassword();
        String address = "123 Test Street, Test City";
        return new TestUser(login, password, address);
    }

    protected String getValidPassword() {
        return "Pa$$word1337";
    }

    protected Response registerUser(TestUser user) throws IOException {
        return registerUser(user, DEFAULT_CLIENT);
    }

    protected Response registerUser(TestUser user, String clientName) throws IOException {
        Map<String, String> registerRequest = Map.of(
                "login", user.login(),
                "password", user.password(),
                "address", user.address()
        );
        return post("/auth/register", registerRequest, clientName);
    }

    protected Response loginUser(TestUser user) throws IOException {
        return loginUser(user, DEFAULT_CLIENT);
    }

    protected Response loginUser(TestUser user, String clientName) throws IOException {
        Map<String, String> loginRequest = Map.of(
                "login", user.login(),
                "password", user.password()
        );
        return post("/auth/login", loginRequest, clientName);
    }

    protected boolean isUserLoggedIn() throws IOException {
        return isUserLoggedIn(DEFAULT_CLIENT);
    }

    protected boolean isUserLoggedIn(String clientName) throws IOException {
        try (Response response = get("/user", clientName)) {
            return response.isSuccessful();
        }
    }

    protected void clearCookies(String clientName) {
        CookieManager cookieManager = cookieManagers.get(clientName);
        if (cookieManager != null) {
            cookieManager.getCookieStore().removeAll();
        }
    }

    protected void clearDefaultCookies() {
        clearCookies(DEFAULT_CLIENT);
    }

    public record TestUser(String login, String password, String address) {}
} 