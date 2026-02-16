package com.oplink.server.controller;

import com.oplink.server.dto.CreateMessageRequest;
import com.oplink.server.dto.MessageResponse;
import com.oplink.server.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels/{channelId}/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    
    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getMessages(
            @PathVariable Long channelId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "50") int limit,
            @AuthenticationPrincipal UserDetails userDetails) {
        Page<MessageResponse> messages = messageService.getMessages(channelId, cursor, limit, userDetails.getUsername());
        return ResponseEntity.ok(messages);
    }
    
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
            @PathVariable Long channelId,
            @Valid @RequestBody CreateMessageRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        MessageResponse response = messageService.createMessage(channelId, request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
