package com.kamilosinni.WorldChat.type.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request object for verifying email
 */
@Data
public class VerifyEmailRequest {
    @NotEmpty
    @Size(min = 6, max = 6)
    private String code;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String username;
}
