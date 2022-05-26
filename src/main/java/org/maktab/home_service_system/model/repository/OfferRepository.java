package org.maktab.home_service_system.model.repository;


import com.sun.xml.bind.v2.model.core.ID;
import org.maktab.home_service_system.model.entity.Offer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Query("FROM Offer o WHERE o.order.Id = :orderId")
    List<Offer> findOrderOffers(@Param("orderId") Integer orderId,Sort sort);
//    List<Offer> findOrderOffers(@Param("orderId") Integer orderId,Sort sort);

    List<Offer> findOfferByOrder_Id(Object orderId, Sort sort);

}
