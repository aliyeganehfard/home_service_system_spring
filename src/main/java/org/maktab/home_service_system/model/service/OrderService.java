package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.util.OrderState;

import java.util.List;

public interface OrderService{
    List<Order> findByOrderState(OrderState orderState);
    Order selectAnExpertToOrder(Order order, Offer offer);
    Order changeOrderState(Order order , OrderState orderState);
}
