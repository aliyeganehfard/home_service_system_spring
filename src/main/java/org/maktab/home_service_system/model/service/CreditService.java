package org.maktab.home_service_system.model.service;

import org.maktab.home_service_system.controller.dto.CreditDto;

import java.util.List;

public interface CreditService {
    CreditDto save(CreditDto creditDto);

    CreditDto findById(Integer id);

    List<CreditDto> findAllCredit();
}
