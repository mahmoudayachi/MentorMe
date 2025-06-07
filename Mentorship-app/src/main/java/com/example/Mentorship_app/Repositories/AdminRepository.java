package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Entities.Admin;
import com.example.Mentorship_app.Entities.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long>{
  Optional<Admin> findFirstByEmail(String username);

}
