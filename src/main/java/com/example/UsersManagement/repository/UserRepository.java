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
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    Optional<User> findByPhoneNumberAndDeletedFalse(String phoneNumber);

    Optional<User> findByIdAndDeletedFalse(Long id);

    @Query(
            value = """
                    select * from "user"
                    where deleted=false
                    and (:firstName is null or first_name =:firstName)
                    and (:lastName is null or last_name =:lastName)
                    and (:phoneNumber is null or phone_number =:phoneNumber)
                    """,
            countQuery= """
                    select count(*)
                    from "user"
                    where deleted=false
                    and (:firstName is null or first_name =:firstName)
                    and (:lastName is null or last_name =:lastName)
                    and (:phoneNumber is null or phone_number =:phoneNumber)
                    """,
            nativeQuery = true
    )
    Page<User> getUsersNativeQuery(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phoneNumber") String phoneNumber,
            Pageable pageable
    );
}
