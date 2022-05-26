package org.maktab.home_service_system.model.service;

import org.maktab.home_service_system.controller.dto.SubServicesDto;

import java.util.List;

public interface SubServicesService {
    SubServicesDto save(SubServicesDto subServicesDto);

    SubServicesDto findById(Integer id);

    List<SubServicesDto> findAllSubServices();
}
