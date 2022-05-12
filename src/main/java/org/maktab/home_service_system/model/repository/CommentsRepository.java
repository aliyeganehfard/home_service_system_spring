package org.maktab.home_service_system.model.repository;

import org.maktab.home_service_system.model.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
