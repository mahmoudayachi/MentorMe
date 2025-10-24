package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Enums.RequestStatus;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import com.example.Mentorship_app.Repositories.MentorshipRequestRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorService implements UserDetailsService {


    public String uploaddirectory = System.getProperty("user.dir") + "./src/main/resources/images";
    private final MentorRepository mentorRepository;
    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final EmailService emailService;
    private final MenteeRepository menteeRepository;


    public MentorService(MentorRepository mentorRepository, MentorshipRequestRepository mentorshipRequestRepository, EmailService emailService, MenteeRepository menteeRepository) {
        this.mentorRepository = mentorRepository;
        this.mentorshipRequestRepository = mentorshipRequestRepository;
        this.emailService = emailService;
        this.menteeRepository = menteeRepository;
    }

    public List<Mentor> Getallmentors(){
        List<Mentor> Allmentorslist = mentorRepository.findAll();
        return Allmentorslist;
    }

    public List<Mentor> searchMentor(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String jobtitle,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> skills){
        List<Mentor> mentorList = mentorRepository.searchMentors(company,jobtitle,category, skills);
        return  mentorList;
    }

    public Optional <MentorshipRequest> updaterequeststatus(Long requestid, RequestStatus requestStatus , Long menteeid) {
        Optional<MentorshipRequest> exisitngrequest = mentorshipRequestRepository.findById(requestid);
        Optional<Mentee> exisitngmentee = menteeRepository.findById(menteeid);
        if (exisitngrequest.isPresent()) {
            MentorshipRequest request = exisitngrequest.get();
            request.setStatus(requestStatus);
            mentorshipRequestRepository.save(request);
            if (requestStatus.toString().equals("ACCEPTED")) {
                emailService.sendmail(exisitngmentee.get().getEmail(), "Mentorship Request", "your Mentorship request has been accepted you can communicate with your mentor now");
            } else if (requestStatus.toString().equals("REFUSED")) {
                emailService.sendmail(exisitngmentee.get().getEmail(), "Mentorship Request", " unfortunatly,your Mentorship request has benn refused  due to the fellow reasons : mentor don't have enough time ");
            }
            return Optional.of(request);
        } else {
            return Optional.empty();
        }

    }


    public Mentor updateMentorProfile(Long mentorId,Mentor updatedmentor, MultipartFile image) throws IOException {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        mentor.setFirstname(updatedmentor.getFirstname());
        mentor.setLastname(updatedmentor.getLastname());
        mentor.setCategory(updatedmentor.getCategory());
        mentor.setLinkedinlink(updatedmentor.getLinkedinlink());
        mentor.setCompany(updatedmentor.getCompany());
        mentor.setLocation(updatedmentor.getLocation());
        mentor.setBio(updatedmentor.getBio());
        mentor.setPassword(new BCryptPasswordEncoder().encode(updatedmentor.getPassword()));
        mentor.setEmail(updatedmentor.getEmail());
        mentor.setSkills(updatedmentor.getSkills());
        if (image != null && !image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploaddirectory,originalFilename);
            Files.write(fileNameAndPath, image.getBytes());
            mentor.setProfileimage(originalFilename);
        }

        return mentorRepository.save(mentor);
    }

    public Optional<Mentor> getmentorbyid(Long id ){
        return mentorRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.mentorRepository.findFirstByEmail(username).orElseThrow(()-> new UsernameNotFoundException("mentor not found"));
    }
}
