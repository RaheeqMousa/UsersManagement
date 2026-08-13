package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers(
            String firstName,
            String lastName,
            String phoneNumber) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class); //tell the criteria whcih entity we are making query for
        List<Predicate> predicates = new ArrayList<>(); //each predicate is a condition

        predicates.add(
               cb.isFalse(user.get("deleted"))
        );

        if(firstName!=null){
            predicates.add(
              cb.equal(user.get("firstName"),firstName) //first name ='Raheeq'
            );
        }
        if(lastName!=null){
            predicates.add(
                    cb.equal(user.get("lastName"), lastName)  //last name ='Mousa'
            );
        }
        if(phoneNumber!=null){
            predicates.add(
                    cb.equal(user.get("phoneNumber"), phoneNumber)  //last name ='Mousa'
            );
        }

        query.where(//means put all the conditions I collected into the where clause
                predicates.toArray(new Predicate[0])
        );

        return entityManager
                .createQuery(query)
                .getResultList();
    }

}
