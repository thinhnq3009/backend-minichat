package com.eco.beminichat.request;

import lombok.Data;


@Data
public class RegisterRequest {


    private String username;

    private String password;

    private String displayName;

}
