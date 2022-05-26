package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.controller.dto.SubServicesDto;
import org.maktab.home_service_system.controller.exception.ServicsesNotFoundException;
import org.maktab.home_service_system.controller.exception.SubServicesNotFoundException;
import org.maktab.home_service_system.model.entity.SubServices;
import org.maktab.home_service_system.model.repository.SubServicesRepository;
import org.maktab.home_service_system.model.service.SubServicesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubServicesServiceImpl implements SubServicesService {
    private final SubServicesRepository subServicesRepository;
    private final ServicesServiceImpl servicesService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(SubServicesServiceImpl.class.getName());

    public SubServicesServiceImpl(SubServicesRepository subServicesRepository,ServicesServiceImpl servicesService, ModelMapper modelMapper) {
        this.subServicesRepository = subServicesRepository;
        this.servicesService = servicesService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubServicesDto save(SubServicesDto subServicesDto){
        var services = servicesService.findServicesById(subServicesDto.getServicesId());
        if (services == null)
            throw new ServicsesNotFoundException();
        var subServices = modelMapper.map(subServicesDto,SubServices.class);
        subServices.setServices(services);
        subServicesRepository.save(subServices);
        logger.info("subServices save by id "+ subServices.getId());
        return modelMapper.map(subServices,SubServicesDto.class);
    }

    @Override
    public SubServicesDto findById(Integer id){
        logger.info("subServices find by id "+id);
        return modelMapper.map(findSubServicesById(id),SubServicesDto.class);
    }

    protected SubServices findSubServicesById(Integer id) {
        return subServicesRepository.findById(id)
                .orElseThrow(() -> new SubServicesNotFoundException(id.toString())
                );
    }


    @Override
    public List<SubServicesDto> findAllSubServices(){
        logger.info("find All subServices");
        return subServicesRepository.findAll().stream()
                .map(subServices -> modelMapper.map(subServices,SubServicesDto.class))
                .collect(Collectors.toList());
    }

    public List<SubServicesDto> findSubServicesByServicesId(Integer servicesId){
        logger.info("find All subServices by service id "+servicesId);
        return subServicesRepository.findByServicesId(servicesId).stream()
                .map(subServices -> modelMapper.map(subServices,SubServicesDto.class))
                .collect(Collectors.toList());
    }

}
