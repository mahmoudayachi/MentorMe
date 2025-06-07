package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Repositories.MenteeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MenteeService implements UserDetailsService {

    private final MenteeRepository menteeRepository;

    public MenteeService(MenteeRepository menteeRepository) {
        this.menteeRepository = menteeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return menteeRepository.findFirstByEmail( username).orElseThrow(()->new UsernameNotFoundException("mentee not found"));
    }
}
