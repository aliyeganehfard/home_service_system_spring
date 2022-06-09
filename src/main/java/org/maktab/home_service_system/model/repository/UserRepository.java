package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.base.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Accounts,Integer> {
    Optional<Accounts> findByUsername(String username);
}
