package com.oplink.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateServerRequest {
    
    @NotBlank(message = "Server name is required")
    @Size(min = 1, max = 100, message = "Server name must be between 1 and 100 characters")
    private String name;
    
    private String description;
    private String iconUrl;
}
