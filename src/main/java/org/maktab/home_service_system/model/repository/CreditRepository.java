package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Integer> {

    @Query("FROM Credit c WHERE c.account.Id = :accountId")
    Credit findCreditByAccountId(@Param("accountId")Integer accountId);
}
