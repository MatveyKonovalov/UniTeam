package com.example.webplatform.data.auth.userdetails;

import com.example.models.security.Role;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull CustomUserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException{
        UserEntity userEntity = userRepository.findUserEntityByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User with email=" + username + " not found"));

        return new CustomUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                getAuthorities(userEntity.getRole())
        );
    }

    // Роли
    private Collection<? extends GrantedAuthority> getAuthorities(Role projectRole){
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + projectRole.name()));
    }
}
