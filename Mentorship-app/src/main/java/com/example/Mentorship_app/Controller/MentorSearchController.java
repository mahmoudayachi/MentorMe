package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Services.jwt.MenteeService;
import com.example.Mentorship_app.Services.jwt.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class MentorSearchController {
    private final MentorService mentorService;
    private final MenteeService menteeService;
    public MentorSearchController(MentorService mentorService, MenteeService menteeService) {
        this.mentorService = mentorService;
        this.menteeService = menteeService;
    }

    @GetMapping("/search")
    public List<Mentor>  searchMentors(
            @RequestParam(required = false) String jobtitle,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> skills
    ) {
        return mentorService.searchMentor(jobtitle, company, category, skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getmentorbyid( @PathVariable  Long id ){
        return ResponseEntity.ok().body(mentorService.getmentorbyid(id));
    }
    @GetMapping("/All")
    public ResponseEntity<?> Getallmentors(){
        return ResponseEntity.ok().body(mentorService.Getallmentors());
    }
    @GetMapping("/Mentee/{id}")
    public ResponseEntity<?> getmenteebyid( @PathVariable  Long id ){
        return ResponseEntity.ok().body(menteeService.getmenteebyid(id));
    }

    @PatchMapping("/update/{mentorId}")
    public ResponseEntity<Mentor> updateMentorProfile(
            @PathVariable Long mentorId,
             @ModelAttribute Mentor updatedmentor,
            @RequestParam(value = "image",required = false) MultipartFile image) {
        try {
            Mentor updateMentor = mentorService.updateMentorProfile(mentorId, updatedmentor,image);
            return ResponseEntity.ok(updateMentor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PatchMapping("/update/mentee/{menteeId}")
    public ResponseEntity<Mentee> updateMenteeProfile(
            @PathVariable Long menteeId,
            @ModelAttribute Mentee updatedmentee) {
        try {
            Mentee updateMentee = menteeService.updateMenteeProfile(menteeId,updatedmentee);
            return ResponseEntity.ok(updateMentee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
