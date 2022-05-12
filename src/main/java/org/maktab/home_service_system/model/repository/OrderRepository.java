package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.util.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByOrderState(OrderState orderState);

}
