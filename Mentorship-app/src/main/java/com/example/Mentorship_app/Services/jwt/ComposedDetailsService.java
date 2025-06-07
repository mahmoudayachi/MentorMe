package com.example.Mentorship_app.Services.jwt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ComposedDetailsService implements UserDetailsService {

    @Autowired
    private final MentorService mentorService;
    @Autowired
    private final AdminService adminService;
    @Autowired
    private final MenteeService menteeService;

    public ComposedDetailsService(MentorService mentorService, AdminService adminService, MenteeService menteeService) {
        this.mentorService = mentorService;
        this.adminService = adminService;
        this.menteeService = menteeService;
    }

    private List<UserDetailsService> services;


    @PostConstruct
    public void setServices() {
        List<UserDetailsService> new_services = new ArrayList<>();
        new_services.add(this.adminService);
        new_services.add(this.mentorService);
        new_services.add(this.menteeService);
        this.services = new_services;
    }


    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (UserDetailsService service : services) {
            try {
                UserDetails user = service.loadUserByUsername(username);
                return user;
            } catch (UsernameNotFoundException e) {
                continue;
            }
        }
        throw new UsernameNotFoundException("User Not Found");
    }
}
