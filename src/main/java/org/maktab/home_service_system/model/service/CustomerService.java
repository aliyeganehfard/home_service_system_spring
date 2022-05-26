package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.CustomerDto;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;

import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    void deleteById(Integer id);

    CustomerDto findById(Integer id);

    List<CustomerDto> findAllCustomer();

    CustomerDto login(LoginRequest loginRequest);

    CustomerDto changePassword(ChangePasswordHolder changePasswordHolder);

    List<CustomerDto> search(CustomerSearchHolder searchHolder);

    List<CustomerDto> gridSearch(CustomerSearchHolder searchHolder);

    boolean isCorrectPassword(String password);

    boolean isCorrectEmail(String email);

    boolean isCorrectUsername(String username);
}
