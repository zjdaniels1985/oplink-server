package com.oplink.server.service;

import com.oplink.server.domain.Channel;
import com.oplink.server.domain.Server;
import com.oplink.server.domain.User;
import com.oplink.server.dto.ChannelResponse;
import com.oplink.server.repository.ChannelRepository;
import com.oplink.server.repository.ServerMembershipRepository;
import com.oplink.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {
    
    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final ServerMembershipRepository membershipRepository;
    private final UserService userService;
    
    public List<ChannelResponse> getServerChannels(Long serverId, String username) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        
        User user = userService.findByUsername(username);
        
        if (!membershipRepository.existsByServerIdAndUserId(serverId, user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        List<Channel> channels = channelRepository.findByServerIdOrderByPositionAsc(serverId);
        
        return channels.stream()
                .map(this::toChannelResponse)
                .collect(Collectors.toList());
    }
    
    public Channel findById(Long id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
    }
    
    private ChannelResponse toChannelResponse(Channel channel) {
        return ChannelResponse.builder()
                .id(channel.getId())
                .serverId(channel.getServer().getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .channelType(channel.getChannelType())
                .position(channel.getPosition())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build();
    }
}
