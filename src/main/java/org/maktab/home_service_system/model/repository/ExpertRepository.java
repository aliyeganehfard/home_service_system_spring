package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpertRepository extends JpaRepository<Expert,Integer> {
    Expert findByUsernameAndPassword(String username, String password);

    boolean existsExpertByEmail(String email);

    boolean existsExpertByUsername(String username);

    boolean existsExpertByPassword(String password);

    List<Expert> findAllByFirstnameOrLastnameOrEmail(String firstname,String lastname, String email);

    List<Expert> findAllByUserState(UserState userState);

}
