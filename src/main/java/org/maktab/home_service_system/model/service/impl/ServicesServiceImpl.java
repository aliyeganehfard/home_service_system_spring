package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.model.entity.Services;
import org.maktab.home_service_system.model.repository.ServicesRepository;
import org.maktab.home_service_system.model.service.ServicesService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ServicesServiceImpl extends GenericServiceImpl<ServicesRepository,Services,Integer> implements ServicesService {

    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        super(servicesRepository);
    }
}
