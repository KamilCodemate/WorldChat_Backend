package com.kamilosinni.WorldChat.type.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request object for user login
 */
@Data
public class LoginRequest {

    /**
     * User's username - used for authentication and identification in application
     */
    @NotEmpty
    @Size(min = 3, max = 20)
    private String usernameOrEmail;

    /**
     * User's password - used for authentication
     */
    @NotEmpty
    @Size(min = 6, max = 40)
    private String password;
}
