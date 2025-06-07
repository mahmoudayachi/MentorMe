package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Dto.SignupRequest;
import com.example.Mentorship_app.Entities.Admin;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Enums.UserRole;
import com.example.Mentorship_app.Repositories.AdminRepository;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final MentorRepository mentorRepository;
    private final AdminRepository adminRepository;
    private final MenteeRepository menteeRepository;
    public AuthServiceImpl (MentorRepository mentorRepository, AdminRepository adminRepository, MenteeRepository menteeRepository){

        this.mentorRepository=mentorRepository;
        this.adminRepository = adminRepository;
        this.menteeRepository = menteeRepository;
    }

    @PostConstruct
    public void createAdminAccount(){
        List<Admin> exisitngAdmin = adminRepository.findAll();
        if(exisitngAdmin.isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("admin@test.com");
            admin.setFirstname("adminnn");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setUserRole(UserRole.ADMIN);
            adminRepository.save(admin);
            System.out.println("admin account created successfully ");
        }else{
            System.out.println("admin account already exists ");
        }
    }


    @Override
    public MentorDto signupUser(SignupRequest signupRequest)  {
        Mentor mentor= new Mentor();
        mentor.setFirstname(signupRequest.getFirstname());
        mentor.setLastname(signupRequest.getLastname());
        mentor.setEmail(signupRequest.getEmail());
        mentor.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        mentor.setJobtitle(signupRequest.getJobtitle());
        mentor.setBio(signupRequest.getBio());
        mentor.setCompany(signupRequest.getCompany());
        mentor.setSkills(signupRequest.getSkills());
        mentor.setUserRole(signupRequest.getUserrole());
         Mentor   createdmentor = mentorRepository.save(mentor);
         return createdmentor.getMentorDto();

    }

    @Override
    public MentorDto signupMentor(SignupRequest signupRequest ){
        Mentor mentor= new Mentor();
        mentor.setFirstname(signupRequest.getFirstname());
        mentor.setLastname(signupRequest.getLastname());
        mentor.setEmail(signupRequest.getEmail());
        mentor.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        mentor.setJobtitle(signupRequest.getJobtitle());
        mentor.setBio(signupRequest.getBio());
        mentor.setCompany(signupRequest.getCompany());
        mentor.setSkills(signupRequest.getSkills());
        mentor.setProfileimage(signupRequest.getProfileimage());
        mentor.setUserRole(signupRequest.getUserrole());
        Mentor   createdmentor = mentorRepository.save(mentor);
       return createdmentor.getMentorDto();
    }

    @Override
    public MenteeDto signupMentee(SignupRequest signupRequest) {
        Mentee mentee = new Mentee();
        mentee.setFirstname(signupRequest.getFirstname());
        mentee.setLastname(signupRequest.getLastname());
        mentee.setEmail(signupRequest.getEmail());
        mentee.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        mentee.setUserRole(signupRequest.getUserrole());
        Mentee createdmentee = menteeRepository.save(mentee);
        return createdmentee.getMenteeDto();

    }
    @Override
    public boolean hasUserWithEmail(String email) {
        return mentorRepository.findFirstByEmail(email).isPresent();
    }
    @Override
    public boolean hasMenteeWithEmail(String email) {
        return menteeRepository.findFirstByEmail(email).isPresent();
    }
}
