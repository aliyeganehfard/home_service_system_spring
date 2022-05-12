package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Comments;
import org.maktab.home_service_system.model.repository.CommentsRepository;
import org.maktab.home_service_system.model.service.CommentsService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CommentsServiceImpl extends GenericServiceImpl<CommentsRepository, Comments,Integer> implements CommentsService {

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        super(commentsRepository);
    }
}
