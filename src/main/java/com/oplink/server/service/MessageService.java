package com.oplink.server.service;

import com.oplink.server.domain.Channel;
import com.oplink.server.domain.Message;
import com.oplink.server.domain.User;
import com.oplink.server.dto.CreateMessageRequest;
import com.oplink.server.dto.MessageResponse;
import com.oplink.server.repository.MessageRepository;
import com.oplink.server.repository.ServerMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final ChannelService channelService;
    private final UserService userService;
    private final ServerMembershipRepository membershipRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Transactional
    public MessageResponse createMessage(Long channelId, CreateMessageRequest request, String username) {
        Channel channel = channelService.findById(channelId);
        User user = userService.findByUsername(username);
        
        // Check if user is a member of the server
        if (!membershipRepository.existsByServerIdAndUserId(channel.getServer().getId(), user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        Message message = Message.builder()
                .channel(channel)
                .user(user)
                .content(request.getContent())
                .build();
        
        message = messageRepository.save(message);
        
        MessageResponse response = toMessageResponse(message);
        
        // Broadcast to WebSocket topic
        messagingTemplate.convertAndSend("/topic/channels/" + channelId + "/messages", response);
        
        return response;
    }
    
    public Page<MessageResponse> getMessages(Long channelId, String cursor, int limit, String username) {
        Channel channel = channelService.findById(channelId);
        User user = userService.findByUsername(username);
        
        // Check if user is a member of the server
        if (!membershipRepository.existsByServerIdAndUserId(channel.getServer().getId(), user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        Pageable pageable = PageRequest.of(0, limit);
        Page<Message> messages;
        
        if (cursor != null && !cursor.isEmpty()) {
            try {
                LocalDateTime cursorTime = LocalDateTime.parse(cursor);
                messages = messageRepository.findByChannelIdAndCreatedAtBeforeOrderByCreatedAtDesc(channelId, cursorTime, pageable);
            } catch (Exception e) {
                throw new RuntimeException("Invalid cursor format. Expected ISO-8601 datetime format.");
            }
        } else {
            messages = messageRepository.findByChannelIdOrderByCreatedAtDesc(channelId, pageable);
        }
        
        return messages.map(this::toMessageResponse);
    }
    
    private MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .channelId(message.getChannel().getId())
                .userId(message.getUser().getId())
                .username(message.getUser().getUsername())
                .content(message.getContent())
                .isEdited(message.getIsEdited())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }
}
