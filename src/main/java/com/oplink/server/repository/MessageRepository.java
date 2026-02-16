package com.oplink.server.repository;

import com.oplink.server.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByChannelIdOrderByCreatedAtDesc(Long channelId, Pageable pageable);
    Page<Message> findByChannelIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long channelId, LocalDateTime cursor, Pageable pageable);
}
