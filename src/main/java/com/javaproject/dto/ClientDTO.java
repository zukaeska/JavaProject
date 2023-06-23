package com.javaproject.dto;

public class ClientDTO {
    private String userName;
    private String password;
    private String email;

    public ClientDTO() {
        // Empty constructor needed for JSON deserialization
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

