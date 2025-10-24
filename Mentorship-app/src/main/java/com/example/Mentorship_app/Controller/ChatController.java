package com.example.Mentorship_app.Controller;


import com.example.Mentorship_app.Entities.Message;
import com.example.Mentorship_app.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/chatting")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;


    @MessageMapping("/chat/{mentorId}/{menteeId}")
    public void sendPrivateMessage(
            @DestinationVariable Long mentorId,
            @DestinationVariable Long menteeId,
            Message message) {

        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);


        messagingTemplate.convertAndSend(
                "/topic/chat/" + mentorId + "/" + menteeId,
                message
        );
    }


    @GetMapping("/messages/{mentorId}/{menteeId}")
    @ResponseBody
    public List<Message> getMessages(
            @PathVariable Long mentorId,
            @PathVariable Long menteeId) {
        return messageRepository.findByMentorIdAndMenteeIdOrderByTimestampAsc(mentorId, menteeId);
    }
}

