package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.controller.dto.CommentDto;
import org.maktab.home_service_system.controller.exception.CommentNotFoundException;
import org.maktab.home_service_system.controller.exception.CustomerNotFoundException;
import org.maktab.home_service_system.controller.exception.ExpertNotFoundException;
import org.maktab.home_service_system.model.entity.Comments;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.repository.CommentsRepository;
import org.maktab.home_service_system.model.service.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final ExpertServiceImpl expertService;
    private final CustomerServiceImpl customerService;
    private final ModelMapper modelMapper;

    public CommentsServiceImpl(CommentsRepository commentsRepository, ExpertServiceImpl expertService,
                               CustomerServiceImpl customerService, ModelMapper modelMapper) {
        this.commentsRepository = commentsRepository;
        this.customerService = customerService;
        this.expertService = expertService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        var expert = expertService.findExpertById(commentDto.getExpertId());
        var customer = customerService.findCustomerById(commentDto.getCustomerId());

        extracted(expert, customer);

        var comment = modelMapper.map(commentDto, Comments.class);

        return modelMapper.map(commentsRepository.save(comment), CommentDto.class);
    }

    private void extracted(Expert expert, Customer customer) {
        if (expert == null)
            throw new ExpertNotFoundException();
        if (customer == null)
            throw new CustomerNotFoundException();
    }

    @Override
    public CommentDto findById(Integer id) {
        return modelMapper.map(findCommentById(id), CommentDto.class);
    }


    protected Comments findCommentById(Integer id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id.toString()));
    }

    @Override
    public List<CommentDto> findAllComments() {
        return commentsRepository.findAll().stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}
