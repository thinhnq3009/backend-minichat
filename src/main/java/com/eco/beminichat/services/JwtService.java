package com.eco.beminichat.services;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.mapper.AccountMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final AccountMapper accountMapper;

    private static final String SECRET_KEY = "8A7D6F43A9C2B5E8164F301B89D7E520A431BEDB263EBC0B6C8E9775DB741FC9";
    private static final int TOKEN_HOUR_LIVE = 24;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public AccountDto extractAccountDto(String token) {
        Claims claims = extractAllClaims(token);

        return AccountDto
                .builder()
                .id(claims.get("id", Long.class))
                .username(claims.getSubject())
                .displayName(claims.get("displayName", String.class))
                .avatarUrl(claims.get("avatarUrl", String.class))
                .build();
    }

    public Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Key getSignInkey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }



    public String generateToken(Map<String,Object> maps, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(maps)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_HOUR_LIVE * 60 * 60 * 1000))
                .signWith(getSignInkey())
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims;

        if (userDetails instanceof Account account) {
            claims = copyAccountToMap(accountMapper.apply(account));
        } else {
            claims = new HashMap<>();
        }

        return generateToken(claims, userDetails);
    }

    public Map<String, Object> copyAccountToMap(AccountDto account) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", account.getId());
        claims.put("displayName", account.getDisplayName());
        claims.put("avatarUrl", account.getAvatarUrl());

        return claims;
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isExpired(token);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails.getUsername());
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
