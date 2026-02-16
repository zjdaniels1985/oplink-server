package com.oplink.server.controller;

import com.oplink.server.dto.TurnCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceController {
    
    @Value("${turn.urls}")
    private String turnUrls;
    
    @Value("${turn.username}")
    private String turnUsername;
    
    @Value("${turn.credential}")
    private String turnCredential;
    
    @PostMapping("/{channelId}/offer")
    public ResponseEntity<Map<String, String>> handleOffer(
            @PathVariable Long channelId,
            @RequestBody Map<String, Object> offer) {
        // Stub implementation - just acknowledge the offer
        Map<String, String> response = new HashMap<>();
        response.put("status", "offer_received");
        response.put("channelId", channelId.toString());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{channelId}/answer")
    public ResponseEntity<Map<String, String>> handleAnswer(
            @PathVariable Long channelId,
            @RequestBody Map<String, Object> answer) {
        // Stub implementation - just acknowledge the answer
        Map<String, String> response = new HashMap<>();
        response.put("status", "answer_received");
        response.put("channelId", channelId.toString());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{channelId}/ice")
    public ResponseEntity<Map<String, String>> handleIceCandidate(
            @PathVariable Long channelId,
            @RequestBody Map<String, Object> iceCandidate) {
        // Stub implementation - just acknowledge the ICE candidate
        Map<String, String> response = new HashMap<>();
        response.put("status", "ice_received");
        response.put("channelId", channelId.toString());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/turn-credentials")
    public ResponseEntity<TurnCredentials> getTurnCredentials() {
        TurnCredentials credentials = TurnCredentials.builder()
                .urls(turnUrls)
                .username(turnUsername)
                .credential(turnCredential)
                .build();
        return ResponseEntity.ok(credentials);
    }
}
