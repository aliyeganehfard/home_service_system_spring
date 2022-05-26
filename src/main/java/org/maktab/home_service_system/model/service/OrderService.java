package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.OrderDto;
import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.util.OrderState;

import java.util.List;

public interface OrderService {
    OrderDto save(OrderDto orderDto);

    Order update(Order order);

    OrderDto findById(Integer id);

    List<OrderDto> findAllOrders();

    List<OrderDto> findByOrderState(OrderState orderState);

    OrderDto selectAnExpertToOrder(Integer orderId, Integer offerId);

    OrderDto changeOrderState(Integer orderId, OrderState orderState);

    List<OrderDto> findAllOrderByCustomerId(Integer customerId);

    List<OrderDto> findSuggestedOrderForExpert(Integer expertId);

    List<OrderDto> findCustomerOrders(Integer customerId, String orderState);

    List<OrderDto> findExpertOrders(Integer expertId);
}
