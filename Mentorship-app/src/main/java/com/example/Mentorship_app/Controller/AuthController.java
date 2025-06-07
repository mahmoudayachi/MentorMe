package com.example.Mentorship_app.Controller;

import com.example.Mentorship_app.Dto.*;
import com.example.Mentorship_app.Entities.Admin;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Repositories.AdminRepository;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import com.example.Mentorship_app.Services.jwt.AuthService;
import com.example.Mentorship_app.Services.jwt.ComposedDetailsService;
import com.example.Mentorship_app.Utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final ComposedDetailsService composedService;
    private final AdminRepository adminRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;

     public String uploaddirectory = System.getProperty("user.dir") + "./src/main/resources/images";

    public AuthController
            (AuthService authService, AuthenticationManager authenticationManager,
             JwtUtil jwtUtil, ComposedDetailsService composedService, AdminRepository adminRepository, MentorRepository mentorRepository, MenteeRepository menteeRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.composedService=composedService;
        this.adminRepository=adminRepository;
        this.mentorRepository = mentorRepository;
        this.menteeRepository = menteeRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody SignupRequest signupRequest) {
        if(signupRequest.getUserrole().name().equals("MENTOR")) {
            if (authService.hasUserWithEmail(signupRequest.getEmail()))
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user already exists with email ");
            MentorDto createdmentordto = authService.signupUser(signupRequest);
            if (createdmentordto == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mentor not created");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdmentordto);
        } else if (signupRequest.getUserrole().name().equals("MENTEE"))
            if (authService.hasMenteeWithEmail(signupRequest.getEmail()))
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Mentee already exists with email ");
            MenteeDto createdmenteedto = authService.signupMentee(signupRequest);
            if (createdmenteedto == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mentee not created");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdmenteedto);


    }

    @PostMapping(value = "/signup/mentor", consumes = { "multipart/form-data" })
    public ResponseEntity<?> singupmentor( @ModelAttribute SignupRequest signupRequest ,@RequestParam("image") MultipartFile image) throws IOException {
        if(authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Mentor already exists with email ");
        String originalFilename = image.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploaddirectory,originalFilename);
        Files.write(fileNameAndPath, image.getBytes());
        signupRequest.setProfileimage(originalFilename);
        MentorDto createdmentordto = authService.signupMentor(signupRequest);
        if (createdmentordto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mentor not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdmentordto);
    }


    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        Path imagePath = Paths.get(uploaddirectory, filename);
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }





    @PostMapping("/Admin/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Admin> optionalUser = adminRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }

    @PostMapping("/Mentor/login")
    public ResponseEntity<?> loginmentor(@RequestBody AuthenticationRequest authenticationRequest) {
        Optional<Mentor> optionalMentor = mentorRepository.findFirstByEmail(authenticationRequest.getEmail());
        if (optionalMentor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mentor not found");
        }

        Mentor mentor = optionalMentor.get();
        if (!"ACTIVATED".equalsIgnoreCase(mentor.getAccountstatus().toString())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is deactivated. verify your email for possible reasons.");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Mentor> optionalUser = mentorRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return  ResponseEntity.ok(authenticationResponse);

    }
    @PostMapping("/Mentee/login")
    public AuthenticationResponse loginmentee(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Mentee> optionalUser = menteeRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }



}
