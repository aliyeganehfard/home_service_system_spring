package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.controller.dto.AdminDto;
import org.maktab.home_service_system.controller.exception.AdminNotFoundException;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.model.entity.Admin;
import org.maktab.home_service_system.controller.exception.BadPasswordException;
import org.maktab.home_service_system.controller.exception.DuplicateUsernameOrEmailException;
import org.maktab.home_service_system.model.repository.AdminRepository;
import org.maktab.home_service_system.model.service.AdminService;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ExpertServiceImpl expertService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl(AdminRepository adminRepository, ExpertServiceImpl expertService, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.expertService = expertService;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        extracted(adminDto);

        Admin admin = modelMapper.map(adminDto, Admin.class);
        admin.setUserState(UserState.ADMIN);
        admin.setDateOfRegister(Date.valueOf(LocalDate.now()));
        adminRepository.save(admin);
        logger.info("save admin with id"+admin.getId());
        return modelMapper.map(admin, AdminDto.class);
    }

    private void extracted(AdminDto adminDto) {
        if (isCorrectUsername(adminDto.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(adminDto.getEmail()))
            throw new DuplicateUsernameOrEmailException();
    }

    @Override
    public AdminDto update(AdminDto adminDtoReq) {
        var admin = modelMapper.map(adminDtoReq, Admin.class);
        return modelMapper.map(adminRepository.save(admin), AdminDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    public AdminDto findById(Integer id) {
        logger.info("find by id " +id);
        return modelMapper.map(findAdminById(id), AdminDto.class);
    }


    protected Admin findAdminById(Integer id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id.toString()));
    }

    @Override
    public List<AdminDto> findAllAdmin() {
        return adminRepository.findAll().stream()
                .map(admin -> modelMapper.map(admin, AdminDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean setExpertState(Integer expertId, String userState) {
        var expert = expertService.findExpertById(expertId);
        expert.setUserState(UserState.valueOf(userState.toUpperCase()));
        expertService.update(expert);
        logger.info("admin set expert ["+expertId +"] state ["+userState+"]");
        return true;
    }

    @Override
    public boolean confirmExpert(Integer expertId) {
        logger.info("admin confirmed expert ["+expertId+"]");
        return setExpertState(expertId,"CONFIRMED");
    }

    @Override
    public AdminDto login(LoginRequest loginRequest) {
        return modelMapper.map(
                adminRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()),
                AdminDto.class
        );
    }

    @Override
    public Admin changePassword(Admin admin, String oldPassword, String newUsername, String newPassword) {
        if (!isCorrectPassword(oldPassword))
            throw new BadPasswordException();
        UserValidator.checkPassword(newPassword);
        admin.setUsername(newUsername);
        admin.setPassword(newPassword);
        return adminRepository.save(admin);
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
