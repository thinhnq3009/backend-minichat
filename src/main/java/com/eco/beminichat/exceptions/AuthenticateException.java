package com.eco.beminichat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticateException extends BadCredentialsException {

    public AuthenticateException(String msg) {
        super(msg);
    }

    public AuthenticateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
