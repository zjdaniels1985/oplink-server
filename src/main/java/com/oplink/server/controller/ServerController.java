package com.oplink.server.controller;

import com.oplink.server.dto.CreateServerRequest;
import com.oplink.server.dto.ServerResponse;
import com.oplink.server.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
public class ServerController {
    
    private final ServerService serverService;
    
    @PostMapping
    public ResponseEntity<ServerResponse> createServer(
            @Valid @RequestBody CreateServerRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        ServerResponse response = serverService.createServer(request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<ServerResponse>> getUserServers(@AuthenticationPrincipal UserDetails userDetails) {
        List<ServerResponse> servers = serverService.getUserServers(userDetails.getUsername());
        return ResponseEntity.ok(servers);
    }
    
    @GetMapping("/{serverId}")
    public ResponseEntity<ServerResponse> getServer(
            @PathVariable Long serverId,
            @AuthenticationPrincipal UserDetails userDetails) {
        ServerResponse response = serverService.getServer(serverId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{serverId}/join")
    public ResponseEntity<ServerResponse> joinServer(
            @PathVariable Long serverId,
            @AuthenticationPrincipal UserDetails userDetails) {
        ServerResponse response = serverService.joinServer(serverId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ServerResponse>> getAllServers() {
        List<ServerResponse> servers = serverService.getAllServers();
        return ResponseEntity.ok(servers);
    }
}
