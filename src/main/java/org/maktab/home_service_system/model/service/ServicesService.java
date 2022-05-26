package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.ServicesDto;

import java.util.List;

public interface ServicesService {
    ServicesDto save(ServicesDto serviceDto);

    ServicesDto findById(Integer id);

    List<ServicesDto> findAllServices();
}
