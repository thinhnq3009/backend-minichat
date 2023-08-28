package com.eco.beminichat.exceptions;

import com.eco.beminichat.response.RegisterException;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private boolean LOG_BUG = true;

    private boolean PRINT_STACK_TRACE = true;

    private void logBug(Exception ex) {
        if (LOG_BUG) {
            logger.error(ex.getMessage());
        }

        if (PRINT_STACK_TRACE) {
            ex.printStackTrace();
        }

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject<Object>> handleException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<ResponseObject<Object>> handleAuthenticateException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseObject<Object>> handleExpiredJwtException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConversationDenyAssessException.class)
    public ResponseEntity<ResponseObject<Object>> handleConversationDenyAssessException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ResponseObject<Object>> handleRegisterException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseObject<Object>> handleAccountNotFoundException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }@ExceptionHandler(RequestAddFriendException.class)
    public ResponseEntity<ResponseObject<Object>> handleRequestAddFriendException(Exception ex) {
        logBug(ex);
        return ResponseObjects
                .getResponseEntity(null, ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }


}


