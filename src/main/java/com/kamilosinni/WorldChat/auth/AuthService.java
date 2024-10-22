package com.kamilosinni.WorldChat.auth;

import com.kamilosinni.WorldChat.entity.User;
import com.kamilosinni.WorldChat.repository.UserRepository;
import com.kamilosinni.WorldChat.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

        String code = emailService.generateVerificationCode(email);


        userRepository.save(user);
        verificationCodes.put(email, code);
        scheduler.schedule(() -> {
            verificationCodes.remove(email);
            userRepository.deleteById(user.getId());
        }, 10, java.util.concurrent.TimeUnit.MINUTES);
        emailService.sendEmail(email, code);


    }


}
