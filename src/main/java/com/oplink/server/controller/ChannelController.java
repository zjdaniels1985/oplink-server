package com.oplink.server.controller;

import com.oplink.server.dto.ChannelResponse;
import com.oplink.server.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers/{serverId}/channels")
@RequiredArgsConstructor
public class ChannelController {
    
    private final ChannelService channelService;
    
    @GetMapping
    public ResponseEntity<List<ChannelResponse>> getServerChannels(
            @PathVariable Long serverId,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ChannelResponse> channels = channelService.getServerChannels(serverId, userDetails.getUsername());
        return ResponseEntity.ok(channels);
    }
}
