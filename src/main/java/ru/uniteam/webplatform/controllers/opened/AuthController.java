package ru.uniteam.webplatform.controllers.opened;

import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.auth.tokens.JwtTokenProvider;
import ru.uniteam.webplatform.data.auth.tokens.TokenService;
import ru.uniteam.webplatform.data.auth.userdetails.CustomUserDetails;
import ru.uniteam.webplatform.data.auth.userdetails.CustomUserDetailsService;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.security.*;
import ru.uniteam.webplatform.data.entities.dto.users.PostUser;
import ru.uniteam.webplatform.data.repositories.UserRepository;
import ru.uniteam.webplatform.data.services.users.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CommonPrefix.PREFIX + "auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthenticationManager authManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          TokenService tokenService,
                          CustomUserDetailsService customUserDetailsService,
                          UserMapper mapper) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = customUserDetailsService;
        this.tokenService = tokenService;
        this.userMapper = mapper;
    }

    @PostMapping("/register")// регистрация
    public ResponseEntity<?> register(@RequestBody PostUser user) {
        if (userRepository.findUserEntityByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("The user with email=" + user.getEmail() + " exists");
        }

        String password = passwordEncoder.encode(user.getPassword());
        UserEntity userEntity = userMapper.toUserEntityFromPostUser(user);
        userEntity.setPassword(password);
        userRepository.save(userEntity);

        return new ResponseEntity<>(userMapper.toGetUserFromUserEntity(userEntity), HttpStatus.CREATED);
    }

    @PostMapping("/login")// вход
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // проверка на пароль
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
            String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

            tokenService.saveRefreshTokenInDB(request.email(), refreshToken);
            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.refreshToken();

            if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
                return ResponseEntity.status(401).body(new ErrorResponse("Refresh token is not valid"));
            }
            String userEmail = jwtTokenProvider.getEmailFromToken(refreshToken);
            if (!tokenService.validateRefreshToken(userEmail, refreshToken)) {
                return ResponseEntity.status(401).body("The refresh token not found");
            }

            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);

            return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
        } catch (Exception e){
            return ResponseEntity.status(401).body(new ApiResponse("Refresh token is not valid"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        tokenService.revokeRefreshToken(email);
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(new ApiResponse("You have successfully logged out"));
    }
}
