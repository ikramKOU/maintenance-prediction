package ocp.maintenance.prediction.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ocp.maintenance.prediction.model.RoleEmploye;

public class RegisterDto {


    @NotEmpty
    private String username;
     @NotEmpty
     @Size(min=6,message="AT LEAST DKHEL KTERMN 6")
    private String password;
     @NotEmpty
    private String nom;
     @NotEmpty
    private String phone;
     @NotEmpty
    private String email;
     @NotEmpty
    private RoleEmploye role;

    public RegisterDto() {
    }

    public RegisterDto(String username, String password, String nom, String phone, String email, RoleEmploye role) {
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    // Getters et Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEmploye getRole() {
        return role;
    }

    public void setRole(RoleEmploye role) {
        this.role = role;
    }
    
}
