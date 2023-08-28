package com.eco.beminichat.services;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.exceptions.AuthenticateException;
import com.eco.beminichat.mapper.AccountMapper;
import com.eco.beminichat.repositories.AccountRepository;
import com.eco.beminichat.request.LoginRequest;
import com.eco.beminichat.request.RegisterRequest;
import com.eco.beminichat.response.AuthenticateResponse;
import com.eco.beminichat.response.RegisterException;
import jakarta.servlet.http.HttpServletRequest;
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

    private final AccountMapper accountMapper;

    private final HttpServletRequest request;


    public AuthenticateResponse register(
            @NonNull RegisterRequest request
    ) {

        Optional<Account> existedAccount = accountRepository.findByUsername(request.getUsername());

        if (existedAccount.isPresent()) {
            throw new RegisterException("Username is already existed");
        }

        Account account = Account
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .build();

        accountRepository.save(account);

        String token = jwtService.generateToken(account);

        AuthenticateResponse response = new AuthenticateResponse();

        response.setToken(token);
        response.setUser(accountMapper.apply(account));

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

        String token = jwtService.generateToken(account.get());

        AuthenticateResponse response = new AuthenticateResponse();

        response.setToken(token);
        response.setUser(accountMapper.apply(account.get()));

        return response;
    }


    public AuthenticateResponse authenticate(String token) {
        if (token == null || token.isEmpty()) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            } else {
                throw new AuthenticateException("Can't get token from request. Authentication fail");
            }
        }

        AccountDto account = jwtService.extractAccountDto(token);

        AuthenticateResponse response = new AuthenticateResponse();

        response.setToken(token);
        response.setUser(account);

        return response;
    }
}
