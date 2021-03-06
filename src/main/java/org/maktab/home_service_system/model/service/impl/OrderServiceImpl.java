package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.controller.dto.OrderDto;
import org.maktab.home_service_system.controller.exception.*;
import org.maktab.home_service_system.controller.util.OrderGridSearchHolder;
import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.repository.OrderRepository;
import org.maktab.home_service_system.model.service.OrderService;
import org.maktab.home_service_system.model.util.OfferState;
import org.maktab.home_service_system.model.util.OrderState;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OfferServiceImpl offerService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

    public OrderServiceImpl(OrderRepository orderRepository, OfferServiceImpl offerService,
                            ModelMapper modelMapper, EntityManager entityManager) {
        this.orderRepository = orderRepository;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public OrderDto save(OrderDto orderDto) {
        var order = modelMapper.map(orderDto, Order.class);

        order.setDateOfRegistration(Date.valueOf(LocalDate.now()));
        order.setOrderState(OrderState.WAITING_FOR_EXPERT_SUGGESTION);

        if (isInCorrectDate(order))
            throw new IncorrectOrderDateException();
        orderRepository.save(order);

        logger.info("order save with id " + order.getId());
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Order update(Order order) {
        if (isInCorrectDate(order))
            throw new IncorrectOrderDateException();
        return orderRepository.save(order);
    }

    @Override
    public OrderDto findById(Integer id) {
        logger.info("order find by id " + id);
        return modelMapper.map(orderRepository.findById(id), OrderDto.class);
    }


    @Override
    public List<OrderDto> findAllOrders() {
        logger.info("find all orders");
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByOrderState(OrderState orderState) {
        logger.info("find order by order state " + orderState);
        return orderRepository.findAllByOrderState(orderState).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto selectAnExpertToOrder(Integer orderId, Integer offerId) {
        var order = findOrderById(orderId);
        var offer = offerService.findOfferById(offerId);

        extracted(order, offer);

        offer.setOfferState(OfferState.ACCEPTED);
        order.setOrderState(OrderState.WAITING_FOR_EXPERT_TO_COME);
        order.setPrice(offer.getSuggestedPrice());

        offerService.update(offer);

        logger.info("select an expert wit offer id " + offerId + " to order id " + orderId);
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public List<OrderDto> findAllOrderByCustomerId(Integer customerId) {
        logger.info("find all order by customer id " + customerId);
        return orderRepository.findAllOrderByCustomerId(customerId).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findSuggestedOrderForExpert(Integer expertId) {
        logger.info("find suggested order for expert wit id " + expertId);
        return orderRepository.findSuggestedOrderForExpert(expertId).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findCustomerOrders(Integer customerId, String orderState) {
        logger.info("find customer " + customerId + " orders with order state " + orderState);
        return orderRepository.findCustomerOrdersWithOrderState(customerId, OrderState.valueOf(orderState)).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findExpertOrders(Integer expertId) {
        logger.info("find expert orders " + expertId);
        return orderRepository.findExpertOrders(expertId).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    private void extracted(Order order, Offer offer) {
        if (order == null)
            throw new OrderNotFoundException();
        if (offer == null)
            throw new OfferNotFoundException();
    }

    protected Order findOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString())
                );
    }

    @Override
    public OrderDto changeOrderState(Integer orderId, OrderState orderState) {
        var order = findOrderById(orderId);
        if (order == null)
            throw new OrderNotFoundException();

        order.setOrderState(orderState);

        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    public List<Order> findFinishedOrderWithCustomerId(Integer customerId) {
        return orderRepository.findCustomerOrdersWithOrderState(customerId, OrderState.DONE);
    }

    public List<Order> gridSearch(OrderGridSearchHolder holder) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Order.class);
        var root = criteriaQuery.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();

        if (holder.getAfterOf() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfRegistration"),holder.getAfterOf()));

        if (holder.getBeforeOf() != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfRegistration"),holder.getBeforeOf()));

        if (holder.getOrderState() != null)
            predicates.add(criteriaBuilder.equal(root.get("orderState"), holder.getOrderState()));

        if (holder.getSubServiceName() != null && !holder.getSubServiceName().isEmpty())
            predicates.add(criteriaBuilder.equal(
                            root.join("subServices").get("name"), holder.getSubServiceName()
                    )
            );

        if (holder.getServiceName() != null && !holder.getServiceName().isEmpty())
            predicates.add(
                    criteriaBuilder.equal(
                            root.join("subServices").join("services").get("name"), holder.getServiceName()
                    )
            );

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    public Order finishOfOrder(Integer orderId) {
        var order = findOrderById(orderId);
        order.setOrderState(OrderState.DONE);
        return update(order);
    }

    public Integer numberOfCustomerOrders(Integer customerId){
        return findAllOrderByCustomerId(customerId).size();
    }


    private boolean isInCorrectDate(Order order) {
        return order.getDateOfWork().before(order.getDateOfRegistration());
    }
}
