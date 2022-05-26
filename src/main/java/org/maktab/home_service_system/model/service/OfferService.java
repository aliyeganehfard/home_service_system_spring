package org.maktab.home_service_system.model.service;

import org.maktab.home_service_system.controller.dto.OfferDto;

import java.util.List;

public interface OfferService {
    OfferDto save(OfferDto offerDto);

    OfferDto findById(Integer id);

    List<OfferDto> findAllOffers();

    List<OfferDto> findOrderOffers(Integer orderId);
}
