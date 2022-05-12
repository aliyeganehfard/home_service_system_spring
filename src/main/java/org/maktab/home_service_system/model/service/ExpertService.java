package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Services;
import org.maktab.home_service_system.model.util.UserState;

import java.util.List;

public interface ExpertService{
    void addExpertToService(Expert expert, Services services);

    Expert deleteExpertOfService(Expert expert, Services services);

    Expert login(String username, String password);

    Expert changePassword(Expert expert,String oldPassword, String newUsername, String newPassword);

    List<Expert> search(String firstname, String lastname, String email);

    List<Expert> findByUserState(UserState userState);

    boolean isCorrectPassword(String password);

    boolean isCorrectEmail(String email);

    boolean isCorrectUsername(String username);

}
