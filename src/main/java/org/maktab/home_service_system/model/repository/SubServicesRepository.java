package org.maktab.home_service_system.model.repository;


import org.maktab.home_service_system.model.entity.SubServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServicesRepository extends JpaRepository<SubServices,Integer> {
}
