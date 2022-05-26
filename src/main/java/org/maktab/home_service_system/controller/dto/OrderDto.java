package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.util.OrderState;

import javax.validation.constraints.Min;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Long price;
    private String description;
    private Date dateOfRegistration;
    private Date dateOfWork;
    private Time timeOfWork;
    private String address;
    private OrderState orderState;
    private Integer customerId;
    private Integer subServicesId;
    private String subServicesName;
}
