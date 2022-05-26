package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.controller.dto.ServicesDto;
import org.maktab.home_service_system.controller.exception.ServicsesNotFoundException;
import org.maktab.home_service_system.model.entity.Services;
import org.maktab.home_service_system.model.repository.ServicesRepository;
import org.maktab.home_service_system.model.service.ServicesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesServiceImpl implements ServicesService {
    private final ServicesRepository servicesRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ServicesServiceImpl.class.getName());

    public ServicesServiceImpl(ServicesRepository servicesRepository, ModelMapper modelMapper) {
        this.servicesRepository = servicesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServicesDto save(ServicesDto serviceDto) {
        var service = modelMapper.map(serviceDto, Services.class);
        servicesRepository.save(service);
        logger.info("services save"+service.getId());
        return modelMapper.map(service, ServicesDto.class);
    }

    @Override
    public ServicesDto findById(Integer id) {
        logger.info("services find by id "+id);
        return modelMapper.map(findServicesById(id), ServicesDto.class);
    }

    protected Services findServicesById(Integer id) {
        return servicesRepository.findById(id)
                .orElseThrow(() -> new ServicsesNotFoundException(id.toString())
                );
    }

    @Override
    public List<ServicesDto> findAllServices() {
        logger.info("find all Services");
        return servicesRepository.findAll().stream()
                .map(service -> modelMapper.map(service, ServicesDto.class))
                .collect(Collectors.toList());
    }
}
