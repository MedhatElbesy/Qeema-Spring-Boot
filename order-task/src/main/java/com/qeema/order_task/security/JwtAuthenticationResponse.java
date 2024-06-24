package com.qeema.order_task.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationResponse {
    private String token;
    private String role;

    
}
