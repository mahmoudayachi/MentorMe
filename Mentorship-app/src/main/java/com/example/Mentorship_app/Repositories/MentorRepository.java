package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Dto.MentorDto;
import com.example.Mentorship_app.Entities.Admin;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor,Long>{
  Optional<Mentor> findById(Long id);
  Optional<Mentor> findFirstByEmail(String username);
  List<Mentor> findAll();

  @Query("SELECT m FROM Mentor m JOIN m.skills s " +
          "WHERE m.jobtitle LIKE %:jobtitle% " +
          "OR m.company LIKE %:company% " +
          "OR m.category LIKE %:category% " +
          "OR s IN :skills AND m.accountstatus =ACTIVATED")
  List<Mentor> searchMentors(@Param("jobtitle") String jobtitle,
                             @Param("company") String company,
                             @Param("category") String category,
                             @Param("skills") List<String> skills);
}
