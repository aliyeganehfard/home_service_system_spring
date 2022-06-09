package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.util.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByOrderState(OrderState orderState);

    @Query("FROM Order o WHERE o.customer.Id = :customerId")
    List<Order> findAllOrderByCustomerId(@Param("customerId") Integer customerId);

    @Query(value = "SELECT * FROM orders o\n" +
            "join sub_services ss on ss.id = o.sub_services_id\n" +
            "join services s on ss.services_id = s.id\n" +
            "join expert_services es on s.id = es.services_id\n" +
            "where es.expert_id = :id", nativeQuery = true)
    List<Order> findSuggestedOrderForExpert(@Param("id") Integer id);

    @Query("FROM Order o WHERE o.customer.Id = :customerId AND o.orderState = :orderState")
    List<Order> findCustomerOrdersWithOrderState(@Param("customerId") Integer customerId, @Param("orderState") OrderState orderState);

    @Query(value = "SELECT * FROM orders\n" +
            "JOIN offer o on orders.id = o.order_id\n" +
            "JOIN accounts a on o.expert_id = a.id\n" +
            "WHERE expert_id = :expertId", nativeQuery = true)
    List<Order> findExpertOrders(@Param("expertId") Integer expertId);

}
