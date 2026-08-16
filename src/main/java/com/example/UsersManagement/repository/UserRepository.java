package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"addresses"})
    Optional<User> findByPhoneNumberAndDeletedFalse(String phoneNumber);

    @EntityGraph(attributePaths = {"addresses"})
    Optional<User> findByIdAndDeletedFalse(Long id);

    @EntityGraph(attributePaths = {"addresses"})
    List<User> findByDeletedFalse();

    @EntityGraph(attributePaths = {"addresses"})
    Page<User> findByDeletedFalse(Pageable pageable);
}
