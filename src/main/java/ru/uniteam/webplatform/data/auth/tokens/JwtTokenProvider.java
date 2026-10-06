package ru.uniteam.webplatform.data.auth.tokens;

import ru.uniteam.webplatform.data.auth.userdetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtTokenProvider {
    private static final String TOKEN_TYPE = "type";
    private static final String ID = "id";
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";
    private static final String AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKeySpec secretKeySpec;

    @PostConstruct
    private void init() {
        secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HS256.getJcaName());
    }

    // Access token
    public String generateAccessToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(AUTHORITIES, getClaimsFromAuthorities(userDetails.getAuthorities()))
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .claim(ID, userDetails.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(secretKeySpec, HS256)
                .compact();
    }

    // Refresh Token
    public String generateRefreshToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .claim(ID, userDetails.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(secretKeySpec, HS256)
                .compact();
    }

    // Get userEmail from token
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Get User id
    public String getUserIdFromToken(String token) {
        return parseClaims(token).getId();
    }

    // get token type
    public String getTokenType(String token){
        try{
            return parseClaims(token).get(TOKEN_TYPE, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    // validateToken
    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
    // check token type
    public boolean isAccessToken(String token){
        return ACCESS_TOKEN.equals(getTokenType(token)) && validateToken(token);
    }

    public boolean isRefreshToken(String token){
        return REFRESH_TOKEN.equals(getTokenType(token)) && validateToken(token);
    }

    // Get parsing data from token
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKeySpec)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("ERROR in parsing: " + e.getMessage());
        }
    }

    private List<String> getClaimsFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
