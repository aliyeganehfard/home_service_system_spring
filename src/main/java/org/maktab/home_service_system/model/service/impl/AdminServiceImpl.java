package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.model.entity.Admin;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.exception.BadPasswordException;
import org.maktab.home_service_system.model.exception.DuplicateUsernameOrEmailException;
import org.maktab.home_service_system.model.repository.AdminRepository;
import org.maktab.home_service_system.model.service.AdminService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class AdminServiceImpl extends GenericServiceImpl<AdminRepository,Admin,Integer> implements AdminService {

    private final AdminRepository adminRepository;
    private final ExpertServiceImpl expertService;

    public AdminServiceImpl(AdminRepository adminRepository, ExpertServiceImpl expertService) {
        super(adminRepository);
        this.adminRepository = adminRepository;
        this.expertService = expertService;
    }

    @Override
    public Admin save(Admin admin){
        if (isCorrectUsername(admin.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(admin.getEmail()))
            throw new DuplicateUsernameOrEmailException();

        admin.setUserState(UserState.ADMIN);
        admin.setDateOfRegister(Date.valueOf(LocalDate.now()));
        return super.save(admin);
    }

    @Override
    public void setExpertState(Expert expert, UserState userState) {
        try {
            expert.setUserState(userState);
            expertService.update(expert);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Admin login(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public Admin changePassword(Admin admin,String oldPassword, String newUsername, String newPassword) {
        if (!isCorrectPassword(oldPassword))
            throw new BadPasswordException();
        UserValidator.checkPassword(newPassword);
        admin.setUsername(newUsername);
        admin.setPassword(newPassword);
        return super.update(admin);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return adminRepository.existsAdminByPassword(password);
    }

    @Override
    public boolean isCorrectUsername(String username) {
        return adminRepository.existsAdminByUsername(username);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return adminRepository.existsAdminByEmail(email);
    }
}
