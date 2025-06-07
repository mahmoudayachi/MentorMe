package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Dto.SignupRequest;

import java.io.IOException;

public interface AuthService {
    MentorDto signupUser(SignupRequest signupRequest);
    MentorDto signupMentor(SignupRequest signupRequest) throws IOException;
    MenteeDto signupMentee(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);
    boolean hasMenteeWithEmail(String email);
}
