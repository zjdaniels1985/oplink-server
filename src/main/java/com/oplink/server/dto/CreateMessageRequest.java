package com.oplink.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMessageRequest {
    
    @NotBlank(message = "Message content is required")
    private String content;
}
