package com.example.Mentorship_app.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String status;
    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private String mentorName;
    private Long mentorId;
    private String menteeName;
    private Long menteeId;

    public SessionDTO(Long id, String name, Double price, String description,
                      String status, LocalDateTime scheduledAt, Integer durationMinutes,
                      String mentorName, Long mentorId, String menteeName, Long menteeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.durationMinutes = durationMinutes;
        this.mentorName = mentorName;
        this.mentorId = mentorId;
        this.menteeName = menteeName;
        this.menteeId = menteeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(Long menteeId) {
        this.menteeId = menteeId;
    }
}
