package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(int id);

    @Query("SELECT u from User u join Role r on r.id = u.role.id\n" +
            " where (:type_account IS NULL OR (r.name=:type_account))\n" +
            "and u.name like %:query% and (:status IS NULL OR u.status=:status)")
    Page<User> findAll(@Param("query") String query,
                       @Param("status") int status,
                       @Param("type_account") String typeAccount,
                       Pageable pageable);
}
