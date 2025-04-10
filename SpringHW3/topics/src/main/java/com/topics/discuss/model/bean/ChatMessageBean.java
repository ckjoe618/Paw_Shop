package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity @Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private int memberId;
    private String messageContent;
    private LocalDateTime createdDate;
}
