package org.maktab.home_service_system.model.repository;


import org.maktab.home_service_system.model.entity.SubServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServicesRepository extends JpaRepository<SubServices,Integer> {

    @Query("FROM SubServices sub WHERE sub.services.Id = :id")
    List<SubServices> findByServicesId(@Param("id") Integer servicesId);
//    List<SubServices> findAllByServices_Id(Object servicesId);
}
