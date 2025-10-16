package com.example.Mentorship_app.Dto;

import com.example.Mentorship_app.Entities.Mentee;

import java.time.LocalDateTime;

public class BookingRequestDTO {
    private Mentee mentee;
    private LocalDateTime scheduledAt;

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
