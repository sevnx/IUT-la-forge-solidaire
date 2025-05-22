package desforge.dev.modals.auth;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    public RegisterRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
