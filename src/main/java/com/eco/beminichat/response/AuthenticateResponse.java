package com.eco.beminichat.response;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.response.base.ResponseEnable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse implements ResponseEnable {
    String token;

    AccountDto user;

}
