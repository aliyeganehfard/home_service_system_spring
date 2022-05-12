package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.exception.BadPasswordException;
import org.maktab.home_service_system.model.exception.DuplicateUsernameOrEmailException;
import org.maktab.home_service_system.model.repository.CustomerRepository;
import org.maktab.home_service_system.model.service.CustomerService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceImpl extends GenericServiceImpl<CustomerRepository, Customer,Integer> implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer){
        if (isCorrectUsername(customer.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(customer.getEmail()))
            throw new DuplicateUsernameOrEmailException();

        customer.setUserState(UserState.NEW);
        customer.setDateOfRegister(Date.valueOf(LocalDate.now()));
        return super.save(customer);
    }
    @Override
    public Customer login(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Customer changePassword(Customer customer,String oldPassword ,String newUsername, String newPassword) {
        if (!isCorrectPassword(oldPassword))
            throw new BadPasswordException();
        if (isCorrectUsername(newUsername))
            throw new DuplicateUsernameOrEmailException();
        UserValidator.checkPassword(newPassword);
        customer.setPassword(newPassword);
        customer.setUsername(newUsername);
        return super.update(customer);
    }

    @Override
    public List<Customer> search(String firstname, String lastname, String email) {
        return customerRepository.findAllByFirstnameOrLastnameOrEmail(firstname,lastname,email);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return customerRepository.existsCustomersByPassword(password);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean isCorrectUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
}
