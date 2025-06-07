package com.example.Mentorship_app.Entities;


import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
public class Mentor extends User  {

    private String skills;
    private String company;
    private String category;
    private String jobtitle;
    private String bio;
    private String profileimage;
    private String linkedinlink;

    public String getLinkedinlink() {
        return linkedinlink;
    }

    public void setLinkedinlink(String linkedinlink) {
        this.linkedinlink = linkedinlink;
    }

    @Enumerated(EnumType.STRING)
    private AccountStatus accountstatus = AccountStatus.DESACTIVATED;

    public AccountStatus getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(AccountStatus accountstatus) {
        this.accountstatus = accountstatus;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(super.getUserRole().name()));
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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


    public MentorDto getMentorDto(){
       MentorDto mentorDto = new MentorDto();
       mentorDto.setId(super.getId());
       mentorDto.setFirstname(super.getFirstname());
       mentorDto.setLastname(super.getLastname());
       mentorDto.setPassword(super.getPassword());
       mentorDto.setEmail(super.getEmail());
       mentorDto.setBio(bio);
       mentorDto.setCategory(category);
       mentorDto.setJobtitle(jobtitle);
       mentorDto.setSkills(skills);
       mentorDto.setCompany(company);
       mentorDto.setUserRole(super.getUserRole());
       mentorDto.setLocation(super.getLocation());
       mentorDto.setImage(profileimage);
       mentorDto.setAccountStatus(accountstatus);
       return mentorDto;
    }
}