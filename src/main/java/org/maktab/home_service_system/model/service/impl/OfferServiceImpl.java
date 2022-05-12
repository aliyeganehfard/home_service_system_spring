package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.repository.OfferRepository;
import org.maktab.home_service_system.model.service.OfferService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.util.OrderState;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OfferServiceImpl extends GenericServiceImpl<OfferRepository, Offer, Integer> implements OfferService {
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        super(offerRepository);
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer save(Offer offer) {
        offer.setDateOfRegistration(Date.valueOf(LocalDate.now()));
        offer.getOrder().setOrderState(OrderState.WAITING_FOR_EXPERT_SELECTION);
        return super.save(offer);
    }

    @Override
    public List<Offer> findOrderOffers(Integer orderId) {
        return offerRepository.findOrderOffers(orderId);
    }
}
