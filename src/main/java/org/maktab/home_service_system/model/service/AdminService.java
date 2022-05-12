package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.model.entity.Admin;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.util.UserState;

public interface AdminService {
    void setExpertState(Expert expert, UserState userState);

    Admin login(String username, String password);

    Admin changePassword(Admin admin,String oldPassword, String newUsername, String newPassword);

    boolean isCorrectPassword(String password);

    boolean isCorrectUsername(String username);

    boolean isCorrectEmail(String email);
}
