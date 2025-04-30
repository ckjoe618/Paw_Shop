package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.response.ChatMessageDto;
import com.topics.discuss.model.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessageDto> getChatMessages() {
        return chatMessageRepository.findTop10Messages();
    }
}
