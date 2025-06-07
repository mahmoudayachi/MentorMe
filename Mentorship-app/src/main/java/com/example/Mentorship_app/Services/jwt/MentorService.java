package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Repositories.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorService implements UserDetailsService {

    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    public List<Mentor> searchMentors(
            @RequestParam(required = false) String jobtitle,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String skills
    )

    {
        List<Mentor> mentorList = mentorRepository.findByJobtitleContainsOrCompanyContainsOrCategoryContainsOrSkillsContains( jobtitle,company,category,skills);
       return mentorList;
     }

    public List<Mentor> searchMentor(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String jobtitle,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String skills){
        List<Mentor> mentorList = mentorRepository.searchMentors(company,jobtitle,category,skills);
        return  mentorList;
    }
    public Optional<Mentor> getmentorbyid(Long id ){
        return mentorRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.mentorRepository.findFirstByEmail(username).orElseThrow(()-> new UsernameNotFoundException("mentor not found"));
    }
}
