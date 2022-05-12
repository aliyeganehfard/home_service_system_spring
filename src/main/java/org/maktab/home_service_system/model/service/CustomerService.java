package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer login(String username, String password);

    Customer changePassword(Customer customer, String oldPassword, String newUsername, String newPassword);

    List<Customer> search(String firstname, String lastname, String email);

    boolean isCorrectPassword(String password);

    boolean isCorrectEmail(String email);

    boolean isCorrectUsername(String username);
}
