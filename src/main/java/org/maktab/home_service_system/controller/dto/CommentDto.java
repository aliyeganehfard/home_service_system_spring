package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.util.Score;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    private String comment;
    private Score score;
    private Integer customerId;
    private Integer expertId;
}
