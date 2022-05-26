package org.maktab.home_service_system.model.service;

import org.maktab.home_service_system.controller.dto.CommentDto;

import java.util.List;

public interface CommentsService {
    CommentDto save(CommentDto commentDto);

    CommentDto findById(Integer id);

    List<CommentDto> findAllComments();
}
