package com.kamilosinni.WorldChat.controller;

import com.kamilosinni.WorldChat.auth.AuthService;
import com.kamilosinni.WorldChat.entity.User;
import com.kamilosinni.WorldChat.type.request.LoginRequest;
import com.kamilosinni.WorldChat.type.request.RegisterRequest;
import com.kamilosinni.WorldChat.type.request.VerifyEmailRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterRequest registerRequest) {

        authService.registerUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());

        return ResponseEntity.ok("USER_REGISTERED");

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        Authentication authResponse = authenticationManager.authenticate(authentication);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authResponse);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
        return ResponseEntity.ok("USER_LOGGED_IN");
    }

    @PostMapping("/api/auth/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody @Validated VerifyEmailRequest verifyEmailRequest, @AuthenticationPrincipal User user) {
        String principalUsername = user.getUsername();
        String requestUsername = verifyEmailRequest.getUsername();

        if (!principalUsername.equals(requestUsername)) {
            return ResponseEntity.badRequest().body("INVALID_USERNAME");
        }

        return ResponseEntity.ok("EMAIL_VERIFIED");
    }
}
