package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Normalized;
import org.maktab.home_service_system.model.util.TransactionType;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Integer id;
    private Date date;
    private Time time;
    private Long amount;
    private TransactionType transactionType;
    private Integer senderId;
    private Integer receiverId;
}
