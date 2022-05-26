package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.util.OfferState;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private Integer id;
    private Date dateOfRegistration;
    private Time timeOfRegistration;
    private Long suggestedPrice;
    private Integer durationOfWork;
    private Time timeOfWork;
    private OfferState offerState;
    private Integer expertId;
    private String expertFirstname;
    private String expertLastname;
    private Integer expertScore;
    private Integer orderId;
}
