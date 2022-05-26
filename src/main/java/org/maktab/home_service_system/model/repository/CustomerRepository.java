package org.maktab.home_service_system.model.repository;


import org.hibernate.criterion.CriteriaQuery;
import org.maktab.home_service_system.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByUsernameAndPassword(String username,String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsCustomersByPassword(String password);

    List<Customer> findAllByFirstnameOrLastnameOrEmail(String firstname , String lastname , String email);

}
