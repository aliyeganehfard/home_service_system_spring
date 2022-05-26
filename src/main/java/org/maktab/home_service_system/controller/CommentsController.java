package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.CommentDto;
import org.maktab.home_service_system.model.service.impl.CommentsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsServiceImpl commentsService;

    public CommentsController(CommentsServiceImpl commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentsService.save(commentDto));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable String id){
        return ResponseEntity.ok(commentsService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CommentDto>> findAll(){
        return ResponseEntity.ok(commentsService.findAllComments());
    }
}
