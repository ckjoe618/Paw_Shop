package com.topics.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topics.discuss.model.entity.ChatMessage;
import com.topics.discuss.model.repository.ChatMessageRepository;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;
import com.topics.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatMessageRepository chatMessageRepository;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    private final Map<WebSocketSession, Integer> sessionMemberMap = new HashMap<>();

    public ChatWebSocketHandler(ChatMessageRepository chatMessageRepository, JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = getTokenFromQuery(session.getUri().getQuery());
        Integer memberId = getMemberIdFromToken(token);
        if (memberId == null) {
            session.close();
            return;
        }
        sessionMemberMap.put(session, memberId);
        System.out.println("使用者 " + memberId + " 已連線聊天室");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Integer memberId = sessionMemberMap.get(session);
        if (memberId == null) return;

        String json = message.getPayload().toString();
        Map<String, String> msgMap = objectMapper.readValue(json, Map.class);
        String content = msgMap.get("content");
        String memberName = memberRepository.findById(memberId)
                .map(MemberBean::getMemberName) // 如果你是 nickname 請改成 getNickname
                .orElse("未知使用者");

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMemberId(memberId);
        chatMessage.setContent(content);
        chatMessage.setCreatedDate(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        String broadcastJson = objectMapper.writeValueAsString(Map.of(
                "memberId", memberId,
                "memberName", memberName,
                "content", content,
                "createdDate", chatMessage.getCreatedDate().toString()
        ));

        for (WebSocketSession s : sessionMemberMap.keySet()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(broadcastJson));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("WebSocket 錯誤：" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer memberId = sessionMemberMap.remove(session);
        System.out.println("使用者 " + memberId + " 已離線");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getTokenFromQuery(String query) {
        if (query == null) return null;
        for (String part : query.split("&")) {
            if (part.startsWith("token=")) {
                return part.substring("token=".length());
            }
        }
        return null;
    }

    private Integer getMemberIdFromToken(String token) {
        try {
            String memberIdStr = jwtUtil.getSubject(token); // 用你的 getSubject
            return Integer.parseInt(memberIdStr);
        } catch (Exception e) {
            return null;
        }
    }
}
