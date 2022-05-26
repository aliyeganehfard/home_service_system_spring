package org.maktab.home_service_system.model.service.impl;


import org.hibernate.criterion.Distinct;
import org.maktab.home_service_system.controller.dto.OfferDto;
import org.maktab.home_service_system.controller.exception.ExpertNotFoundException;
import org.maktab.home_service_system.controller.exception.OfferNotFoundException;
import org.maktab.home_service_system.controller.exception.OrderNotFoundException;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.repository.OfferRepository;
import org.maktab.home_service_system.model.service.OfferService;
import org.maktab.home_service_system.model.util.OfferState;
import org.maktab.home_service_system.model.util.OrderState;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper,
                            @Lazy ExpertServiceImpl expertService, @Lazy OrderServiceImpl orderService) {
        this.offerRepository = offerRepository;
        this.expertService = expertService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OfferDto save(OfferDto offerDto) {
        var expert = expertService.findExpertById(offerDto.getExpertId());
        var order = orderService.findOrderById(offerDto.getOrderId());

        checked(expert, order);
        var offer = modelMapper.map(offerDto, Offer.class);
        offer.setDateOfRegistration(Date.valueOf(LocalDate.now()));
        offer.getOrder().setOrderState(OrderState.WAITING_FOR_EXPERT_SELECTION);
        offer.setOfferState(OfferState.Waiting);
        offer.setExpert(expert);
        offer.setOrder(order);
        offerRepository.save(offer);

        logger.info("save offer with id "+offer.getId());
        return modelMapper.map(offer, OfferDto.class);
    }

    private void checked(Expert expert, Order order) {
        if (expert == null)
            throw new ExpertNotFoundException();
        if (order == null)
            throw new OrderNotFoundException();
    }

    protected Offer update(Offer offer){
        return offerRepository.save(offer);
    }
    @Override
    public OfferDto findById(Integer id) {
        logger.info("find offer by id "+id);
        return modelMapper.map(findOfferById(id), OfferDto.class);
    }

    protected Offer findOfferById(Integer id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id.toString())
                );
    }

    @Override
    public List<OfferDto> findAllOffers() {
        logger.info("find all offers");
        return offerRepository.findAll().stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> findOrderOffers(Integer orderId){
        logger.info("find order "+orderId+" offers");
        return offerRepository.findOrderOffers(orderId,Sort.by(Sort.Direction.DESC,"suggestedPrice")).stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }
}
