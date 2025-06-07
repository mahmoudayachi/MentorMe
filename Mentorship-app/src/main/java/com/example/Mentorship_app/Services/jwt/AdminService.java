package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Repositories.AdminRepository;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.Mentorship_app.Enums.AccountStatus.ACTIVATED;

@Service

public class AdminService implements UserDetailsService {


    private final AdminRepository adminRepository;
    private final MenteeRepository menteeRepository;
    private final MentorRepository mentorRepository;
    private final EmailService emailService;
    public AdminService(AdminRepository adminRepository, MenteeRepository menteeRepository, MentorRepository mentorRepository, EmailService emailService) {
        this.adminRepository = adminRepository;
        this.menteeRepository = menteeRepository;
        this.mentorRepository = mentorRepository;
        this.emailService = emailService;
    }

    public List<Mentee> getAllmentee(){
        List<Mentee>  allmentee = menteeRepository.findAll();
        return allmentee;
     }

     public List<Mentee> getbyName( String name){
        List<Mentee> mentee = menteeRepository.findByfirstname(name);
        return mentee;
     }

     public List<Mentor> getAllmentors(){
         List<Mentor>  allmentors = mentorRepository.findAll();
         return allmentors;
     }

    public Optional <Mentor> updatementorstatus(Long id, AccountStatus accountstatus) {
        Optional<Mentor> exisitngmentor = mentorRepository.findById(id);
        if(exisitngmentor.isPresent()){
            Mentor mentor = exisitngmentor.get();
            mentor.setAccountstatus(accountstatus);
            mentorRepository.save(mentor);
            if(accountstatus.toString().equals("ACTIVATED")){
                emailService.sendmail(exisitngmentor.get().getEmail(),"account verification done","your account has been activated you can login now");
            }
            else if (accountstatus.toString().equals("DESACTIVATED")){
                emailService.sendmail(exisitngmentor.get().getEmail(),"account verification done","your account has been desactivated ");
            }
            return Optional.of(mentor);
        }else{
            return Optional.empty();
        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findFirstByEmail(username).orElseThrow(()->new UsernameNotFoundException("admin not found"));
    }
}
