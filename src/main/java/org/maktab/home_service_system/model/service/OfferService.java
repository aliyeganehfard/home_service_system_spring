package org.maktab.home_service_system.model.service;

import org.maktab.home_service_system.model.entity.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> findOrderOffers(Integer orderId);
}
