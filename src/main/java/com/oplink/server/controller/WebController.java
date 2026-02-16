package com.oplink.server.controller;

import com.oplink.server.dto.ServerResponse;
import com.oplink.server.dto.UserResponse;
import com.oplink.server.service.ServerService;
import com.oplink.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    
    private final UserService userService;
    private final ServerService serverService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/servers";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserResponse user = userService.getCurrentUser(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("/servers")
    public String servers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<ServerResponse> userServers = serverService.getUserServers(userDetails.getUsername());
        List<ServerResponse> allServers = serverService.getAllServers();
        model.addAttribute("userServers", userServers);
        model.addAttribute("allServers", allServers);
        return "servers";
    }
    
    @GetMapping("/servers/{serverId}")
    public String serverDetail(@PathVariable Long serverId, 
                              @AuthenticationPrincipal UserDetails userDetails, 
                              Model model) {
        ServerResponse server = serverService.getServer(serverId, userDetails.getUsername());
        model.addAttribute("server", server);
        return "server-detail";
    }
    
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        List<ServerResponse> servers = serverService.getAllServers();
        model.addAttribute("servers", servers);
        return "admin";
    }
}
