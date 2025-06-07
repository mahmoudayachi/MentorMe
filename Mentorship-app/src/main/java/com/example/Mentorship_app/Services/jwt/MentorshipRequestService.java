package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import com.example.Mentorship_app.Entities.User;
import com.example.Mentorship_app.Enums.RequestStatus;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import com.example.Mentorship_app.Repositories.MentorshipRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MentorshipRequestService {
    private final MentorshipRequestRepository requestRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;


    public MentorshipRequestService(MentorshipRequestRepository requestRepository, MentorRepository mentorRepository, MenteeRepository menteeRepository) {
        this.requestRepository = requestRepository;
        this.mentorRepository = mentorRepository;
        this.menteeRepository = menteeRepository;
    }
    public Optional<MentorshipRequest> Getrequest(Long id ){
        Optional<MentorshipRequest>  allrequests = requestRepository.findByMentorId(id);
        return allrequests;
    }

    public MentorshipRequest sendRequest(MentorshipRequestDTO dto) throws AccessDeniedException, ChangeSetPersister.NotFoundException {



       Mentee menteet  = menteeRepository.findById(dto.getMenteeId()).orElseThrow();
       Mentor mentort  = mentorRepository.findById(dto.getMentorId()).orElseThrow();
        MentorshipRequest request = new MentorshipRequest();
        request.setMentee(menteet);
        request.setMentor(mentort);
        request.setMessage(dto.getMessage());
        request.setStatus(RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());

       return requestRepository.save(request);
    }
}