package com.eco.beminichat.response.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class ResponseObject<T extends  ResponseEnable> {
    private String message;

    private String status;

    private Integer statusCode;

    private T data;
}
