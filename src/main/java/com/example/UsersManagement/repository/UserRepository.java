package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByPhoneNumberAndDeletedFalse(String phoneNumber);

    Optional<User> findByIdAndDeletedFalse(Long id);

    @Query("""
        select u
        from User u
        where (:firstName is null or u.firstName = :firstName)
        and (:lastName is null or u.lastName = :lastName)
        and (:phoneNumber is null or u.phoneNumber = :phoneNumber)
        and u.deleted = false
        """)
    Page<User> getUsersJPQL(@Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("phoneNumber") String phoneNumber,
                        Pageable pageable);

}
