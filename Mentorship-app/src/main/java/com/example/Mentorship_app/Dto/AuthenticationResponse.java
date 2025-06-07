package com.example.Mentorship_app.Dto;

import com.example.Mentorship_app.Enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt ;
    private Long userId;
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
