package com.eco.beminichat.services;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.exceptions.AuthenticateException;
import com.eco.beminichat.mapper.AccountMapper;
import com.eco.beminichat.repositories.AccountRepository;
import com.eco.beminichat.request.LoginRequest;
import com.eco.beminichat.request.RegisterRequest;
import com.eco.beminichat.response.AuthenticateResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(
            @NonNull RegisterRequest request
    ) {

        Account account = Account
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .build();

       try {
           accountRepository.save(account);
       } catch (Exception  e) {
           e.printStackTrace();
           throw new AuthenticateException(e.getMessage());
       }

        String token = jwtService.generateToken( account);

        AuthenticateResponse response = new AuthenticateResponse();

        response.setToken(token);
        response.setAccountInfo(new AccountMapper().apply(account));

        return response;
    }

    public AuthenticateResponse login(
            @NonNull LoginRequest request
    ) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new AuthenticateException("Password is incorrect");
        }

        Optional<Account> account = accountRepository.findByUsername(request.getUsername());

        assert account.isPresent();

        String token = jwtService.generateToken( account.get());

        AuthenticateResponse response = new AuthenticateResponse();

        response.setToken(token);
        response.setAccountInfo(new AccountMapper().apply(account.get()));

        return response;
    }


}
