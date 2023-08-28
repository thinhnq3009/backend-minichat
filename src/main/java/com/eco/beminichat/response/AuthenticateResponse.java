package com.eco.beminichat.response;

import com.eco.beminichat.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {
    String token;

    AccountDto user;

}
