package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Services.jwt.AdminService;
import com.example.Mentorship_app.Services.jwt.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")

public class AdminController {

    private final AdminService adminService;
    private final EmailService emailService;
    public AdminController(AdminService adminService, EmailService emailService) {
        this.adminService = adminService;
        this.emailService = emailService;
    }

    @GetMapping("mentee/all")
    public ResponseEntity<List<Mentee>> getAllmentee(){
       return ResponseEntity.ok().body(adminService.getAllmentee());
    }
    @GetMapping("mentor/all")
    public ResponseEntity<List<Mentor>> getAllmentor(){
        return ResponseEntity.ok().body(adminService.getAllmentors());
    }

    @GetMapping("mentee/{name}")
    public ResponseEntity<List<Mentee>> getmenteeByName(@PathVariable String name ){
        return ResponseEntity.ok().body(adminService.getbyName(name));
    }

    @PutMapping("/update/{id}/{accountStatus}")
    public ResponseEntity<Optional<Mentor>> updatementorstatus(@PathVariable Long id , @PathVariable AccountStatus accountStatus){
        return ResponseEntity.ok().body(adminService.updatementorstatus(id,accountStatus));
    }

}
