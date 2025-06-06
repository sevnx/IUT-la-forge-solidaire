package desforge.dev.models.auth;

import jakarta.validation.constraints.NotBlank;

public class Login {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public Login() {
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

}
