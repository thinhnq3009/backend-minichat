package com.eco.beminichat.response.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseObjects {

    public static <T> ResponseEntity<ResponseObject<T>> getResponseEntity(T t, String message, HttpStatus status) {
        ResponseObject<T> responseObject = new ResponseObject<>();
        responseObject.setData(t);
        responseObject.setMessage(message);
        responseObject.setStatus(status.getReasonPhrase());
        return ResponseEntity.status(status).body(responseObject);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getResponseEntity(T t, String message) {
        return getResponseEntity(t, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getResponseEntity(T t, HttpStatus status) {
        return getResponseEntity(t, null, status);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getResponseEntity(String message, HttpStatus status) {
        return getResponseEntity(null, message, status);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getResponseEntity(T t) {
        return getResponseEntity(t, null, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getFailedResponseEntity(String message, HttpStatus status) {
        return getResponseEntity(message, status);
    }

    public static <T> ResponseEntity<ResponseObject<T>> getFailedResponseEntity(String message) {
        return getResponseEntity(message, HttpStatus.BAD_REQUEST);
    }


}
