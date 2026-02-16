package com.oplink.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelResponse {
    private Long id;
    private Long serverId;
    private String name;
    private String description;
    private String channelType;
    private Integer position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
