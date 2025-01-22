package com.example.spring_mvc_demo.repository;

import com.example.spring_mvc_demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    @Query("FROM User")
    Page<User> findAllUsersHQL(Pageable pageable);

    @Query(value = "SELECT * FROM users ur where ur.username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users ur where ur.username =:username and ur.password=:password", nativeQuery = true)
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    boolean existsByEmailAndPhoneNumber(String email, String  phoneNumber);

    User findUserByUserId(Long userID);

    @Query(value = "SELECT * FROM USERS " +
            "WHERE LOWER(FIRST_NAME) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "   OR LOWER(LAST_NAME) LIKE LOWER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(*) FROM USERS " +
                    "WHERE LOWER(FIRST_NAME) LIKE LOWER(CONCAT('%', :name, '%')) " +
                    "   OR LOWER(LAST_NAME) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true)
    Page<User> findAllUsersByName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM USERS " +
            "WHERE LOWER(FIRST_NAME) LIKE LOWER('%' || :name || '%') " +
            "   OR LOWER(LAST_NAME) LIKE LOWER('%' || :name || '%')",
            nativeQuery = true)
    List<User> filterByName(@Param("name") String name);




}
