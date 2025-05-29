package ocp.maintenance.prediction.dto;

import jakarta.validation.constraints.NotEmpty;

public class LoginDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
      public LoginDto() { }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
