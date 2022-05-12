package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.exception.IncorrectOrderDateException;
import org.maktab.home_service_system.model.repository.OrderRepository;
import org.maktab.home_service_system.model.service.OrderService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.util.OfferState;
import org.maktab.home_service_system.model.util.OrderState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl extends GenericServiceImpl<OrderRepository, Order, Integer> implements OrderService {
    private final OrderRepository orderRepository;
    private final OfferServiceImpl offerService;

    public OrderServiceImpl(OrderRepository orderRepository, OfferServiceImpl offerService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.offerService = offerService;
    }


    @Override
    public Order save(Order order) {
        order.setDateOfRegistration(Date.valueOf(LocalDate.now()));
        order.setOrderState(OrderState.WAITING_FOR_EXPERT_SUGGESTION);
        if (isInCorrectDate(order))
            throw new IncorrectOrderDateException();
        return super.save(order);
    }

    @Override
    public Order update(Order order) {
        if (isInCorrectDate(order))
            throw new IncorrectOrderDateException();
        return super.update(order);
    }

    @Override
    public List<Order> findByOrderState(OrderState orderState) {
        return orderRepository.findAllByOrderState(orderState);
    }

    @Override
    @Transactional
    public Order selectAnExpertToOrder(Order order, Offer offer) {
        offer.setOfferState(OfferState.ACCEPTED);
        order.setOrderState(OrderState.WAITING_FOR_EXPERT_TO_COME);
        order.setPrice(offer.getSuggestedPrice());
        offerService.update(offer);
        return super.save(order);
    }

    @Override
    public Order changeOrderState(Order order, OrderState orderState) {
        order.setOrderState(orderState);
        return super.update(order);
    }

    private boolean isInCorrectDate(Order order) {
        return order.getDateOfWork().before(order.getDateOfRegistration());
    }
}
