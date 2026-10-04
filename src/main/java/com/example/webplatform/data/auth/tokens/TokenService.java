package com.example.webplatform.data.auth.tokens;

import com.example.datastructure.Pair;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Value("${jwt.refresh-token-expiration}")
    private long expirationRefreshToken;

    // Сохранение токена
    public void saveRefreshTokenInDB(String email, String refreshToken) {
        userRepository.updateUserEntityRefreshTokenByEmail(refreshToken, // сам токен
                LocalDateTime.now().plusSeconds(expirationRefreshToken / 1000), // дата конца жизни рефреш токена
                email);
    }

    // Получение токена
    public String getRefreshTokenByEmail(String email) {
        return getUserByEmail(email).getRefreshToken();
    }

    public Pair<String, LocalDateTime> getTokenInfo(String email) {
        UserEntity user = getUserByEmail(email);
        return new Pair<>(user.getRefreshToken(), user.getRefreshTokenExpire());
    }

    // Валидация токена
    public boolean validateRefreshToken(String email, String refreshToken) {
        try {
            Pair<String, LocalDateTime> tokenInfo = getTokenInfo(email);
            // Проверка на null
            if (tokenInfo.first() == null || tokenInfo.second() == null) {
                return false;
            }
            return tokenInfo.first().equals(refreshToken)
                    && tokenInfo.second().isAfter(LocalDateTime.now())
                    && tokenProvider.validateToken(refreshToken);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Удаление токена
    public void revokeRefreshToken(String email) {
        userRepository.updateUserEntityRefreshTokenByEmail(null, null, email);
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email=" + email + " not found"));
    }
}
