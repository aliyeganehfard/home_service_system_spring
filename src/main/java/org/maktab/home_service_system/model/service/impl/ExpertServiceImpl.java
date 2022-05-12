package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Services;
import org.maktab.home_service_system.model.exception.BadPasswordException;
import org.maktab.home_service_system.model.exception.DuplicateUsernameOrEmailException;
import org.maktab.home_service_system.model.exception.ExpertAccessDeniedException;
import org.maktab.home_service_system.model.exception.ServiceNotExistException;
import org.maktab.home_service_system.model.repository.ExpertRepository;
import org.maktab.home_service_system.model.service.ExpertService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpertServiceImpl extends GenericServiceImpl<ExpertRepository,Expert,Integer> implements ExpertService {
    private final ExpertRepository expertRepository;

    public ExpertServiceImpl(ExpertRepository expertRepository) {
        super(expertRepository);
        this.expertRepository = expertRepository;
    }

    @Override
    public Expert save(Expert expert){
        if (isCorrectUsername(expert.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(expert.getEmail()))
            throw new DuplicateUsernameOrEmailException();

        expert.setUserState(UserState.WAITING_FOR_CONFIRMATION);
        expert.setDateOfRegister(Date.valueOf(LocalDate.now()));
        expert.setScore(0);
        return super.save(expert);
    }
    @Override
    public void addExpertToService(Expert expert, Services services) {
        if (expert.getUserState().equals(UserState.WAITING_FOR_CONFIRMATION))
            throw new ExpertAccessDeniedException();
        expert.getServices().add(services);
        expert.setUserState(UserState.ACTIVE);
        super.update(expert);
    }

    @Override
    public Expert deleteExpertOfService(Expert expert, Services services) {
        boolean state = expert.getServices().remove(services);
        if (state)
            super.update(expert);
        else
            throw new ServiceNotExistException();
        return expert;
    }

    @Override
    public Expert login(String username, String password) {
        return expertRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Expert changePassword(Expert expert,String oldPassword, String newUsername, String newPassword) {
        if (!isCorrectPassword(oldPassword))
            throw new BadPasswordException();
        if (isCorrectUsername(newUsername))
            throw new DuplicateUsernameOrEmailException();
        UserValidator.checkPassword(newPassword);
        expert.setUsername(newUsername);
        expert.setPassword(newPassword);
        return super.update(expert);
    }

    @Override
    public List<Expert> search(String firstname, String lastname, String email) {
        return expertRepository.findAllByFirstnameOrLastnameOrEmail(firstname,lastname,email);
    }

    @Override
    public List<Expert> findByUserState(UserState userState) {
        return expertRepository.findAllByUserState(userState);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return expertRepository.existsExpertByPassword(password);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return expertRepository.existsExpertByEmail(email);
    }

    @Override
    public boolean isCorrectUsername(String username) {
        return expertRepository.existsExpertByUsername(username);
    }

}
