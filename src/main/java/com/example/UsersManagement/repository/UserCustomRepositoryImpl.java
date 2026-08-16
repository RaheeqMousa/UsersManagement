package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> getUsers(
            String firstName,
            String lastName,
            String phoneNumber,
            Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class); //tell the criteria whcih entity we are making query for
        List<Predicate> predicates = new ArrayList<>(); //each predicate is a condition

        predicates.add(
               cb.isFalse(user.get("deleted"))
        );

        if(firstName!=null && !firstName.trim().isEmpty()){
            predicates.add(
              cb.equal(user.get("firstName"),firstName) //first name ='Raheeq'
            );
        }
        if(lastName!=null && !lastName.trim().isEmpty()){
            predicates.add(
                    cb.equal(user.get("lastName"), lastName)  //last name ='Mousa'
            );
        }
        if(phoneNumber!=null && !phoneNumber.trim().isEmpty()){
            predicates.add(
                    cb.equal(user.get("phoneNumber"), phoneNumber)  //last name ='Mousa'
            );
        }

        query.where(//means put all the conditions I collected into the where clause
                predicates.toArray(new Predicate[0])
        );

        TypedQuery<User> typedQuery =
                entityManager.createQuery(query);


        typedQuery.setFirstResult( //to skip users from previous pages
                (int) pageable.getOffset()
        );

        typedQuery.setMaxResults(
                pageable.getPageSize()  //number of users to return for this page
        );

        List<User> users = typedQuery.getResultList(); //get users for current page


        CriteriaQuery<Long> countQuery =
                cb.createQuery(Long.class);

        Root<User> countRoot =
                countQuery.from(User.class);

        countQuery.select(
                cb.count(countRoot)
        );

        List<Predicate> countPredicates =
                new ArrayList<>();

        countPredicates.add(
                cb.isFalse(countRoot.get("deleted"))
        );

        if (firstName != null && !firstName.trim().isEmpty()) {
            countPredicates.add(
                    cb.equal(
                            countRoot.get("firstName"),
                            firstName
                    )
            );
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            countPredicates.add(
                    cb.equal(countRoot.get("lastName"),
                            lastName
                    )
            );
        }

        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            countPredicates.add(
                    cb.equal(
                            countRoot.get("phoneNumber"),
                            phoneNumber
                    )
            );
        }

        countQuery.where(
                countPredicates.toArray(new Predicate[0])
        );



        Long total = entityManager
                .createQuery(countQuery)
                .getSingleResult();


        return new PageImpl<>(
                users,
                pageable,
                total
        );

    }

}
