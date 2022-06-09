package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByUsername(String username);

    Admin findByUsernameAndPassword(String username, String password);

    boolean existsAdminByPassword(String password);

    boolean existsAdminByEmail(String email);

    boolean existsAdminByUsername(String username);
}
