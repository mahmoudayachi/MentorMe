package com.example.Mentorship_app.Services.jwt;


import com.example.Mentorship_app.Entities.AvailabilityPeriod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomService {

    @Value("${zoom.clientId}")
    private String clientId;

    @Value("${zoom.clientSecret}")
    private String clientSecret;

    @Value("${zoom.accountId}")
    private String accountId;

    private final RestTemplate rest = new RestTemplate();


    private String cachedToken = null;
    private Instant tokenExpiry = Instant.EPOCH;

    private synchronized String getAccessToken() {
        if (cachedToken != null && Instant.now().isBefore(tokenExpiry.minusSeconds(30))) {
            return cachedToken;
        }

        String url = "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=" + accountId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> resp = rest.exchange(url, HttpMethod.POST, request, Map.class);
        Map body = resp.getBody();
        String token = (String) body.get("access_token");
        Number expiresIn = (Number) body.get("expires_in");
        cachedToken = token;
        tokenExpiry = Instant.now().plusSeconds(expiresIn.longValue());
        return token;
    }

    public ZoomMeetingInfo createMeeting(AvailabilityPeriod period) {
        String token = getAccessToken();
        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Format start time for Zoom (ISO-8601 with timezone)
        ZonedDateTime start = period.getStartTime().atZone(ZoneId.systemDefault());
        String startTimeFormatted = start.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // Prepare request body
        Map<String, Object> request = new HashMap<>();
        request.put("topic", "Mentoring: " + period.getSession().getName());
        request.put("type", 2); // Scheduled meeting
        request.put("start_time", startTimeFormatted);
        request.put("duration", Duration.between(period.getStartTime(), period.getEndTime()).toMinutes());
        request.put("timezone", start.getZone().toString());
        request.put("agenda", "Mentoring session between mentor and mentee.");

        // Optional settings
        Map<String, Object> settings = new HashMap<>();
        settings.put("host_video", true);
        settings.put("participant_video", true);
        settings.put("join_before_host", false);
        settings.put("mute_upon_entry", true);
        settings.put("waiting_room", false);
        request.put("settings", settings);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = rest.postForEntity(url, entity, Map.class);
            Map<String, Object> respBody = response.getBody();

            ZoomMeetingInfo info = new ZoomMeetingInfo();
            info.setMeetingId(String.valueOf(respBody.get("id")));
            info.setJoinUrl((String) respBody.get("join_url"));
            info.setStartUrl((String) respBody.get("start_url"));
            return info;

        } catch (HttpClientErrorException e) {
            System.err.println("Zoom API error: " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to create Zoom meeting", e);
        }
    }
}
