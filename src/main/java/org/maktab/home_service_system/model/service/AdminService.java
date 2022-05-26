package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.AdminDto;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.model.entity.Admin;

import java.util.List;

public interface AdminService {
    AdminDto save(AdminDto adminDto);

    AdminDto update(AdminDto adminDtoReq);

    void deleteById(Integer id);

    AdminDto findById(Integer id);

    List<AdminDto> findAllAdmin();

    boolean setExpertState(Integer expertId, String userState);

    boolean confirmExpert(Integer expertId);

    AdminDto login(LoginRequest loginRequest);

    Admin changePassword(Admin admin, String oldPassword, String newUsername, String newPassword);

    boolean isCorrectPassword(String password);

    boolean isCorrectUsername(String username);

    boolean isCorrectEmail(String email);
}
