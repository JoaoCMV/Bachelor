package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

public class User {

    private String username;
    private String password;
    private String genero;
    private String role;

    public User() {
    }

    public User(String username, String password, String genero, String role) {
        this.username = username;
        this.password = password;
        this.genero = genero;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User:[" + username + ", " + genero + ", " + role + "]";
    }

    public String getUsername() {
        return username;
    }
    public String getGenero() {
        return genero;
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
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
