package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.controller.dto.ExpertDto;
import org.maktab.home_service_system.controller.exception.*;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.ExpertSearchHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Services;
import org.maktab.home_service_system.model.repository.ExpertRepository;
import org.maktab.home_service_system.model.service.ExpertService;
import org.maktab.home_service_system.model.service.validator.UserValidator;
import org.maktab.home_service_system.model.util.UserState;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final ServicesServiceImpl servicesService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(ExpertServiceImpl.class.getName());

    public ExpertServiceImpl(ExpertRepository expertRepository, ServicesServiceImpl servicesService,
                             ModelMapper modelMapper,EntityManager entityManager) {
        this.expertRepository = expertRepository;
        this.servicesService = servicesService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }


    public ExpertDto save(ExpertDto expertDto) {
        Check(expertDto);

        var expert = modelMapper.map(expertDto, Expert.class);

        expert.setUserState(UserState.WAITING_FOR_CONFIRMATION);
        expert.setDateOfRegister(Date.valueOf(LocalDate.now()));
        expert.setScore(0);
        expertRepository.save(expert);
        logger.info("expert save"+ expert.getId());
        return modelMapper.map(expert, ExpertDto.class);
    }

    private void Check(ExpertDto expertDto) {
        if (isCorrectUsername(expertDto.getUsername()))
            throw new DuplicateUsernameOrEmailException();
        if (isCorrectEmail(expertDto.getEmail()))
            throw new DuplicateUsernameOrEmailException();
    }


    public boolean addExpertToService(Integer expertId, Integer servicesId) {
        var services = servicesService.findServicesById(servicesId);
        var expert = findExpertById(expertId);
        Check(services, expert);

        expert.getServices().add(services);
        expert.setUserState(UserState.ACTIVE);
        expertRepository.save(expert);

        logger.info("add expert"+ expertId +" to service"+servicesId);
        return true;
    }

    private void Check(Services services, Expert expert) {
        if (services == null)
            throw new ServicsesNotFoundException();
        if (expert == null)
            throw new ExpertNotFoundException();
    }

    protected Expert findExpertById(Integer id) {
        logger.info("expert find by id"+id);
        return expertRepository.findById(id)
                .orElseThrow(() -> new ExpertNotFoundException(id.toString())
                );
    }

    public ExpertDto findById(Integer id){
        return modelMapper.map(findExpertById(id),ExpertDto.class);
    }

    public List<ExpertDto> findAllExpert(){
        return expertRepository.findAll().stream()
                .map(expert -> modelMapper.map(expert, ExpertDto.class))
                .collect(Collectors.toList());
    }

    public boolean deleteExpertOfService(Integer expertId, Integer servicesId) {
        var services = servicesService.findServicesById(servicesId);
        var expert = findExpertById(expertId);
        Check(services, expert);

        logger.info("delete expert "+ expertId +" of services "+servicesId);
        return expert.getServices().remove(services);
    }

    @Override
    public ExpertDto login(LoginRequest loginRequest) {
        return modelMapper.map(
                expertRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()),
                ExpertDto.class
        );
    }

    @Override
    public ExpertDto changePassword(ChangePasswordHolder holder) {
        var expert = findExpertById(holder.getCustomerId());
        Check(holder, expert);
        UserValidator.checkPassword(holder.getNewPassword());

        expert.setUsername(holder.getUsername());
        expert.setPassword(holder.getNewPassword());
        return modelMapper.map(
                expertRepository.save(expert),
                ExpertDto.class
        );
    }

    private void Check(ChangePasswordHolder holder, Expert expert) {
        if (expert == null)
            throw new ExpertNotFoundException();
        if (!isCorrectPassword(holder.getOldPassword()))
            throw new BadPasswordException();
        if (isCorrectUsername(holder.getUsername()))
            throw new DuplicateUsernameOrEmailException();
    }

    @Override
    public List<ExpertDto> search(CustomerSearchHolder searchHolder) {
        return expertRepository.
                findAllByFirstnameOrLastnameOrEmail(searchHolder.getFirstname(),
                        searchHolder.getLastname(), searchHolder.getEmail()).stream()
                .map(expert -> modelMapper.map(expert, ExpertDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpertDto> gridSearch(ExpertSearchHolder searchHolder) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Expert.class);

        var root = criteriaQuery.from(Expert.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchHolder.getFirstname() != null && !searchHolder.getFirstname().isEmpty())
            predicates.add(criteriaBuilder.equal(root.get("firstname"), searchHolder.getFirstname()));

        if (searchHolder.getLastname() != null && !searchHolder.getLastname().isEmpty())
            predicates.add(criteriaBuilder.equal(root.get("lastname"), searchHolder.getLastname()));

        if (searchHolder.getEmail() != null && !searchHolder.getEmail().isEmpty())
            predicates.add(criteriaBuilder.equal(root.get("email"), searchHolder.getEmail()));

        if (searchHolder.getScore()  != null && !searchHolder.getScore().isEmpty())
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("score"), Integer.valueOf(searchHolder.getScore())));

        if (searchHolder.getServiceName() != null && !searchHolder.getServiceName().isEmpty())
            predicates.add(criteriaBuilder.equal(root.join("services").get("name"), searchHolder.getServiceName()));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        logger.info("expert search"+ searchHolder);
        return entityManager.createQuery(criteriaQuery).getResultList().stream()
                .map(expert -> modelMapper.map(expert, ExpertDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpertDto> findByUserState(UserState userState) {
        return expertRepository.findAllByUserState(userState).stream()
                .map(expert -> modelMapper.map(expert, ExpertDto.class))
                .collect(Collectors.toList());
    }

    public List<ExpertDto> findAllExpertWithWaitingForConfirmationState(){
        logger.info("findAllExpertWithWaitingForConfirmationState");
        return findByUserState(UserState.WAITING_FOR_CONFIRMATION);
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

    protected Expert update(Expert expert) {
        return expertRepository.save(expert);
    }
}
