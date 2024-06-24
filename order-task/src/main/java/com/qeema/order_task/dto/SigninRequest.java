package com.qeema.order_task.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SigninRequest {

    private String email;
    private String password;
}
