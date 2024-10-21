package com.kamilosinni.WorldChat.entity;

import com.kamilosinni.WorldChat.type.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * User's username - used for authentication and identification in application
     */
    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty
    @Size(min = 3, max = 20)
    private String username;

    /**
     * User's email - used for authentication and 2FA
     */
    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 40)
    private String email;

    /**
     * User's role - used for authorization
     */
    @Enumerated
    @Column(name = "role", nullable = false, unique = true)
    private Role role;

    /**
     * User's hashed password - used for authentication
     */
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    /**
     * User's enabled status - used for email verification
     */
    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private boolean enabled = true; //TODO: change to false after implementing email verification

    /**
     * User's locked status - used for banning users
     */
    @Column(name = "locked", nullable = false)
    @Builder.Default
    private boolean locked = false;
    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}