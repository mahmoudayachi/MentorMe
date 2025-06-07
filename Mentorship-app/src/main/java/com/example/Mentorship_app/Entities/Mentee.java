package com.example.Mentorship_app.Entities;


import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
public class Mentee extends User {

    private String objectif;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountstatus = AccountStatus.ACTIVATED;

    public AccountStatus getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(AccountStatus accountstatus) {
        this.accountstatus = accountstatus;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(super.getUserRole().name()));
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public MenteeDto getMenteeDto() {
        MenteeDto menteeDto = new MenteeDto();
        menteeDto.setId(super.getId());
        menteeDto.setFirstname(super.getFirstname());
        menteeDto.setLastname(super.getLastname());
        menteeDto.setPassword(super.getPassword());
        menteeDto.setEmail(super.getEmail());
        menteeDto.setUserRole(super.getUserRole());
        return menteeDto;
    }

}
