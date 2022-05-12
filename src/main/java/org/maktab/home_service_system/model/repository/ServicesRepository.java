package org.maktab.home_service_system.model.repository;


import org.maktab.home_service_system.model.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services,Integer> {
}
