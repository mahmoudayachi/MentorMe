package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Dto.MenteeDto;
import com.example.Mentorship_app.Entities.Admin;
import com.example.Mentorship_app.Entities.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenteeRepository extends JpaRepository<Mentee,Long> {
    Optional<Mentee> findFirstByEmail(String username);
    List<Mentee> findAll();
    List<Mentee> findByfirstname(String name);
}
