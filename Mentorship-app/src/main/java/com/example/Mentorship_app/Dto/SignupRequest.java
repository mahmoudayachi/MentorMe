package com.example.Mentorship_app.Dto;

import com.example.Mentorship_app.Enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<String> skills;
    private String company;
    private String category;
    private String jobtitle;
    private String bio;
    private String linkedinlink;
    private String objectif;
    private String location;
    private UserRole userrole;
    private String profileimage;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedinlink() {

        return linkedinlink;
    }

    public void setLinkedinlink(String linkedinlink) {

        this.linkedinlink = linkedinlink;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public UserRole getUserrole() {
        return userrole;
    }

    public void setUserrole(UserRole userrole) {
        this.userrole = userrole;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
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
}
