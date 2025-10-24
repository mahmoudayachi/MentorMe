package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Enums.RequestStatus;
import com.example.Mentorship_app.Services.jwt.MentorService;
import com.example.Mentorship_app.Services.jwt.MentorshipRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping("/mentorship-requests")
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;
    private final MentorService mentorService;
    public MentorshipRequestController(MentorshipRequestService mentorshipRequestService, MentorService mentorService) {
        this.mentorshipRequestService = mentorshipRequestService;
        this.mentorService = mentorService;
    }

    @PostMapping
    public ResponseEntity<?> sendRequest(@RequestBody MentorshipRequestDTO dto) throws AccessDeniedException, ChangeSetPersister.NotFoundException {

        return ResponseEntity.ok().body(mentorshipRequestService.sendRequest(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetRequest(@PathVariable Long id){
        return ResponseEntity.ok().body(mentorshipRequestService.Getrequest(id));
    }

    @PutMapping("/update/{requestid}/{menteeid}/{requestStatus}")
    public ResponseEntity<Optional<MentorshipRequest>> updaterequeststatus(@PathVariable Long requestid ,@PathVariable Long menteeid, @PathVariable RequestStatus requestStatus){
        return ResponseEntity.ok().body(mentorService.updaterequeststatus(requestid,requestStatus,menteeid));
    }

    @GetMapping("/accepted")
    public ResponseEntity<?> GetAcceptedmentees(){
        return ResponseEntity.ok().body(mentorshipRequestService.GetAllacceptedMentees());
    }
    @GetMapping("/requests/{id}")
    public ResponseEntity<?>  GetrequestsentbyMentee(@PathVariable Long id ){
        return ResponseEntity.ok().body(mentorshipRequestService.GetrequestsentbyMentee(id));
    }

    @GetMapping("/mentors/{id}")
    public ResponseEntity<?>  Getmentorsofmentee(@PathVariable Long id ){
        return ResponseEntity.ok().body(mentorshipRequestService.Getmentorsofmentee(id));
    }

    @GetMapping("/check/{menteeid}/{mentorid}")
    public ResponseEntity<?>  Checkrequest(@PathVariable Long menteeid ,@PathVariable Long mentorid ){
        return ResponseEntity.ok().body(mentorshipRequestService.Checkrequest(menteeid,mentorid));
    }
}
