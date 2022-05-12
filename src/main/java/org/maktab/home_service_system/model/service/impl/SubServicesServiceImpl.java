package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.model.entity.SubServices;
import org.maktab.home_service_system.model.repository.SubServicesRepository;
import org.maktab.home_service_system.model.service.SubServicesService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SubServicesServiceImpl extends GenericServiceImpl<SubServicesRepository,SubServices,Integer> implements SubServicesService {

    public SubServicesServiceImpl(SubServicesRepository subServicesRepository) {
        super(subServicesRepository);
    }
}
