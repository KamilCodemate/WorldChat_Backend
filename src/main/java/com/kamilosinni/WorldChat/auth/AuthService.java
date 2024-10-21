package com.kamilosinni.WorldChat.auth;

import com.kamilosinni.WorldChat.entity.User;
import com.kamilosinni.WorldChat.repository.UserRepository;
import com.kamilosinni.WorldChat.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String email, String password) {
        if (userRepository.findUserByUsernameOrEmail(username).isPresent()) {
            throw new IllegalStateException("USERNAME_TAKEN");
        }
        if (userRepository.findUserByUsernameOrEmail(email).isPresent()) {
            throw new IllegalStateException("EMAIL_TAKEN");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

    }
}
