package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.response.ChatMessageDto;
import com.topics.discuss.model.service.front.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public List<ChatMessageDto> getChatMessages() {
        return chatMessageService.getChatMessages();
    }
}
