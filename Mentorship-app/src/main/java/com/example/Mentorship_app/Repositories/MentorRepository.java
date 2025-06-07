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
  List<Mentor> findByJobtitleContainsOrCompanyContainsOrCategoryContainsOrSkillsContains(String jobtitle,String company , String category ,String skills);
  Optional<Mentor> findById(Long id);
  Optional<Mentor> findFirstByEmail(String username);
  List<Mentor> findAll();

  @Query("SELECT m FROM Mentor m WHERE " +
          "(:jobtitle    LIKE LOWER(CONCAT('%', :jobtitle, '%'))) OR " +
          "(:company  LIKE LOWER(CONCAT('%', :company, '%'))) OR " +
          "(:category   LIKE LOWER(CONCAT('%', :category, '%'))) OR " +
          "(:skills    LIKE LOWER(CONCAT('%', :skills, '%')))")
  List<Mentor> searchMentors(
          @Param("jobtitle") String jobtitle,
          @Param("company") String company,
         @Param("category") String category,
          @Param("skills") String skills
  );
}
