package com.kamilosinni.WorldChat.repository;

import com.kamilosinni.WorldChat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * @param usernameOrEmail
     * @return {@link Optional} of {@link User} with given username or email
     */
    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findUserByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);


}
