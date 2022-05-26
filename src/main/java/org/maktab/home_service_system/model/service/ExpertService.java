package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.ExpertDto;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.ExpertSearchHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;
import org.maktab.home_service_system.model.util.UserState;

import java.util.List;

public interface ExpertService{
    ExpertDto save(ExpertDto expertDto);

    boolean addExpertToService(Integer expertId, Integer servicesId);

    ExpertDto findById(Integer id);

    List<ExpertDto> findAllExpert();

    boolean deleteExpertOfService(Integer expertId, Integer servicesId);

    ExpertDto login(LoginRequest loginRequest);

    ExpertDto changePassword(ChangePasswordHolder changePasswordHolder);

    List<ExpertDto> search(CustomerSearchHolder searchHolder);

    List<ExpertDto> gridSearch(ExpertSearchHolder searchHolder);

    List<ExpertDto> findByUserState(UserState userState);

    boolean isCorrectPassword(String password);

    boolean isCorrectEmail(String email);

    boolean isCorrectUsername(String username);

}
