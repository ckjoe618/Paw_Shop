package com.topics.discuss.model.repository;

import com.topics.discuss.model.dto.response.ChatMessageDto;
import com.topics.discuss.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    @Query("""
        SELECT new com.topics.discuss.model.dto.response.ChatMessageDto(m.memberName, c.content, c.createdDate)
        FROM ChatMessage c
        JOIN MemberBean m ON c.memberId = m.memberId
        ORDER BY c.createdDate ASC
        """)
    List<ChatMessageDto> findTop10Messages();
}
