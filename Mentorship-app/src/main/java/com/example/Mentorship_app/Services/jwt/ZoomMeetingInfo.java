package com.example.Mentorship_app.Services.jwt;

public class ZoomMeetingInfo {
    private String meetingId;
    private String joinUrl;   // for participants (mentees)
    private String startUrl;  // for host (mentor)

    // constructors
    public ZoomMeetingInfo() {}

    public ZoomMeetingInfo(String meetingId, String joinUrl, String startUrl) {
        this.meetingId = meetingId;
        this.joinUrl = joinUrl;
        this.startUrl = startUrl;
    }

    // getters and setters
    public String getMeetingId() { return meetingId; }
    public void setMeetingId(String meetingId) { this.meetingId = meetingId; }

    public String getJoinUrl() { return joinUrl; }
    public void setJoinUrl(String joinUrl) { this.joinUrl = joinUrl; }

    public String getStartUrl() { return startUrl; }
    public void setStartUrl(String startUrl) { this.startUrl = startUrl; }


}
