package com.oplink.server.service;

import com.oplink.server.domain.Server;
import com.oplink.server.domain.ServerMembership;
import com.oplink.server.domain.User;
import com.oplink.server.dto.CreateServerRequest;
import com.oplink.server.dto.ServerResponse;
import com.oplink.server.repository.ServerMembershipRepository;
import com.oplink.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServerService {
    
    private final ServerRepository serverRepository;
    private final ServerMembershipRepository membershipRepository;
    private final UserService userService;
    
    @Transactional
    public ServerResponse createServer(CreateServerRequest request, String username) {
        User owner = userService.findByUsername(username);
        
        Server server = Server.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .owner(owner)
                .build();
        
        server = serverRepository.save(server);
        
        // Auto-join owner to the server
        ServerMembership membership = ServerMembership.builder()
                .server(server)
                .user(owner)
                .build();
        membershipRepository.save(membership);
        
        return toServerResponse(server);
    }
    
    public List<ServerResponse> getUserServers(String username) {
        User user = userService.findByUsername(username);
        List<ServerMembership> memberships = membershipRepository.findByUserId(user.getId());
        
        return memberships.stream()
                .map(membership -> toServerResponse(membership.getServer()))
                .collect(Collectors.toList());
    }
    
    public ServerResponse getServer(Long serverId, String username) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        
        User user = userService.findByUsername(username);
        
        if (!membershipRepository.existsByServerIdAndUserId(serverId, user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        return toServerResponse(server);
    }
    
    @Transactional
    public ServerResponse joinServer(Long serverId, String username) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        
        User user = userService.findByUsername(username);
        
        if (membershipRepository.existsByServerIdAndUserId(serverId, user.getId())) {
            throw new RuntimeException("Already a member");
        }
        
        ServerMembership membership = ServerMembership.builder()
                .server(server)
                .user(user)
                .build();
        membershipRepository.save(membership);
        
        return toServerResponse(server);
    }
    
    public List<ServerResponse> getAllServers() {
        return serverRepository.findAll().stream()
                .map(this::toServerResponse)
                .collect(Collectors.toList());
    }
    
    private ServerResponse toServerResponse(Server server) {
        return ServerResponse.builder()
                .id(server.getId())
                .name(server.getName())
                .description(server.getDescription())
                .ownerId(server.getOwner().getId())
                .ownerUsername(server.getOwner().getUsername())
                .iconUrl(server.getIconUrl())
                .createdAt(server.getCreatedAt())
                .updatedAt(server.getUpdatedAt())
                .build();
    }
}
