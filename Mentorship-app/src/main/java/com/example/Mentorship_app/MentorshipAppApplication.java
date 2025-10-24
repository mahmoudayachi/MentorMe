package com.example.Mentorship_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MentorshipAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentorshipAppApplication.class, args);
	}

}
