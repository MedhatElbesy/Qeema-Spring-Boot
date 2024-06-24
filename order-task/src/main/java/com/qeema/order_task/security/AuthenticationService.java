package com.qeema.order_task.security;

import com.qeema.order_task.dto.SignUpRequest;
import com.qeema.order_task.dto.SigninRequest;
import com.qeema.order_task.user.User;

public interface AuthenticationService {


    User signup(SignUpRequest signUpRequest);
    
    JwtAuthenticationResponse signin (SigninRequest signinRequest);
}
