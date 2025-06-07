package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import com.example.Mentorship_app.Services.jwt.MentorshipRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/mentorship-requests")
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;

    public MentorshipRequestController(MentorshipRequestService mentorshipRequestService) {
        this.mentorshipRequestService = mentorshipRequestService;
    }

    @PostMapping
    public ResponseEntity<?> sendRequest(@RequestBody MentorshipRequestDTO dto) throws AccessDeniedException, ChangeSetPersister.NotFoundException {

        return ResponseEntity.ok().body(mentorshipRequestService.sendRequest(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetRequest(@PathVariable Long id){
        return ResponseEntity.ok().body(mentorshipRequestService.Getrequest(id));
    }
}
