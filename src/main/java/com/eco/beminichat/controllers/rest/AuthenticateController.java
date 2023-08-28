package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.exceptions.AuthenticateException;
import com.eco.beminichat.request.LoginRequest;
import com.eco.beminichat.request.RegisterRequest;
import com.eco.beminichat.response.AuthenticateResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<AuthenticateResponse>> login(
            @RequestBody LoginRequest request
    ) {

        try {
            AuthenticateResponse response = authenticationService.login(request);

            return ResponseObjects.getResponseEntity(response);
        } catch (AuthenticateException e) {
            e.printStackTrace();
            return ResponseObjects.getResponseEntity(null, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObjects.getResponseEntity(null, "Login fail", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject<AuthenticateResponse>> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticateResponse response = authenticationService.register(request);
        return ResponseObjects.getResponseEntity(response, "Register success");
    }

    /**
     * @return <code>AuthenticateResponse</code> response
     */
    @GetMapping("/authenticate")
    public ResponseEntity<ResponseObject<AuthenticateResponse>> register(
            @RequestParam(required = false, name = "token") String token
    ) {
        AuthenticateResponse response = authenticationService.authenticate(token);
        return ResponseObjects.getResponseEntity(response, "Token verification successful");

    }

}
