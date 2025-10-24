package com.example.Mentorship_app.Services.jwt;


import com.example.Mentorship_app.Entities.AvailabilityPeriod;
import com.example.Mentorship_app.Enums.AvailabilityStatus;
import com.example.Mentorship_app.Repositories.AvailabilityPeriodRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionReminderService {
    private final AvailabilityPeriodRepository availibilityperiod;
    private final EmailService emailService;

    public SessionReminderService( AvailabilityPeriodRepository availibilityperiod, EmailService emailService) {
        this.availibilityperiod = availibilityperiod;
        this.emailService = emailService;
    }


    @Scheduled(fixedRate = 900000)
    public void sendSessionReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);


        List<AvailabilityPeriod> upcomingSessions = availibilityperiod.findByStartTimeBetween(now,inOneHour);

        for (AvailabilityPeriod session : upcomingSessions) {


                String menteeEmail = session.getBookedBy().getEmail();
                String mentorEmail = session.getSession().getMentor().getEmail();
                String time = session.getStartTime().toString();

                String subject = "Reminder: Upcoming mentoring session";
                String bodyformentor = "Hello,\n\nThis is a reminder for your mentoring session " + session.getSession().getName() + "scheduled at  " + time +
                        "    with the mentee    " + session.getBookedBy().getFirstname()+"  "+session.getBookedBy().getLastname()+ "  .\n\nBest regards,\nMentoMe Platform";

            String bodyformentee = "Hello,\n\nThis is a reminder for your mentoring session " + session.getSession().getName() + "scheduled at  " + time +
                    "    with the mentor   " + session.getSession().getMentor().getFirstname()+"  "+session.getSession().getMentor().getLastname()+ "  .\n\nBest regards,\nMentoMe Platform";

            emailService.sendmail(mentorEmail, subject, bodyformentor);
                emailService.sendmail(menteeEmail, subject, bodyformentee);

        }
    }
}

