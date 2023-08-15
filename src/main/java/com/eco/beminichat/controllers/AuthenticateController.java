package com.eco.beminichat.controllers;

import com.eco.beminichat.request.LoginRequest;
import com.eco.beminichat.request.RegisterRequest;
import com.eco.beminichat.response.AuthenticateResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticateController {

    private  final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<AuthenticateResponse>> login (
            @RequestBody LoginRequest request
            ) {

        AuthenticateResponse response = authenticationService.login(request);

        return ResponseObjects.getResponseEntity(response);

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject<AuthenticateResponse>> register (
            @RequestBody RegisterRequest request
    ) {

        AuthenticateResponse response = authenticationService.register(request);

        return ResponseObjects.getResponseEntity(response);

    }

}
