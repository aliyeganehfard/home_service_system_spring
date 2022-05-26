package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.controller.dto.CustomerDto;
import org.maktab.home_service_system.controller.exception.*;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.repository.CustomerRepository;
import org.maktab.home_service_system.model.service.CustomerService;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class.getName());

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        extracted(customerDto);

        var customer = modelMapper.map(customerDto, Customer.class);

        customer.setUserState(UserState.NEW);
        customer.setDateOfRegister(Date.valueOf(LocalDate.now()));
        customerRepository.save(customer);
        logger.info("customer save with id"+customer.getId());
        return modelMapper.map(customer, CustomerDto.class);
    }

    private void extracted(CustomerDto customerDto) {
        if (isCorrectUsername(customerDto.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(customerDto.getEmail()))
            throw new DuplicateUsernameOrEmailException();
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        var customer = modelMapper.map(customerDto, Customer.class);
        return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    protected Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto findById(Integer id) {
        logger.info("find by id"+id);
        return modelMapper.map(findCustomerById(id), CustomerDto.class);
    }

    protected Customer findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.toString()));
    }


    @Override
    public List<CustomerDto> findAllCustomer() {
        logger.info("find All customer");
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto login(LoginRequest loginRequest) {
        return modelMapper.map(
                customerRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()),
                CustomerDto.class
        );
    }

    @Override
    public CustomerDto changePassword(ChangePasswordHolder changePasswordHolder) {
        var customer =
                customerRepository.
                        findById(changePasswordHolder.getCustomerId())
                        .orElseThrow(() -> new NotFoundException("2"));

        extracted(changePasswordHolder, customer);

        UserValidator.checkPassword(changePasswordHolder.getNewPassword());

        customer.setPassword(changePasswordHolder.getNewPassword());
        customer.setUsername(changePasswordHolder.getUsername());

        return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    private void extracted(ChangePasswordHolder changePasswordHolder, Customer customer) {
        if (customer == null)
            throw new BadPasswordException();
        if (!isCorrectPassword(changePasswordHolder.getOldPassword()))
            throw new BadPasswordException();
        if (isCorrectUsername(changePasswordHolder.getUsername()))
            throw new DuplicateUsernameOrEmailException();
    }

    @Override
    public List<CustomerDto> search(CustomerSearchHolder searchHolder) {
        return customerRepository.findAllByFirstnameOrLastnameOrEmail(
                        searchHolder.getFirstname(), searchHolder.getLastname(), searchHolder.getEmail()
                ).stream().map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    public List<CustomerDto> gridSearch(CustomerSearchHolder searchHolder) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        var customerRoot = criteriaQuery.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchHolder.getFirstname() != null && !searchHolder.getFirstname().isEmpty())
            predicates.add(criteriaBuilder.equal(customerRoot.get("firstname"), searchHolder.getFirstname()));

        if (searchHolder.getLastname() != null && !searchHolder.getLastname().isEmpty())
            predicates.add(criteriaBuilder.equal(customerRoot.get("lastname"), searchHolder.getLastname()));

        if (searchHolder.getEmail() != null && !searchHolder.getEmail().isEmpty())
            predicates.add(criteriaBuilder.equal(customerRoot.get("email"), searchHolder.getEmail()));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        logger.info("customer search" + searchHolder);
        return entityManager.createQuery(criteriaQuery).getResultList().stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
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
