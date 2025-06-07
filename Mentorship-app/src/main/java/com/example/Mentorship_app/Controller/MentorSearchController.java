package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Services.jwt.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class MentorSearchController {
    private final MentorService mentorService;

    public MentorSearchController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping("/search")
    public List<Mentor>  searchMentors(
            @RequestParam(required = false) String jobtitle,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String skills
    ) {
        return mentorService.searchMentors(jobtitle, company, category, skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getmentorbyid( @PathVariable  Long id ){
        return ResponseEntity.ok().body(mentorService.getmentorbyid(id));
    }
}
