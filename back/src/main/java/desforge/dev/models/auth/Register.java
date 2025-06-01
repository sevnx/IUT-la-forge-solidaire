package desforge.dev.models.auth;

import jakarta.validation.constraints.NotBlank;

public class Register {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    public Register() {
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
