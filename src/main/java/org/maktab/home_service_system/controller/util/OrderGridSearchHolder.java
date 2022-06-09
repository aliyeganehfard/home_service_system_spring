package org.maktab.home_service_system.controller.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.util.OrderState;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderGridSearchHolder {
    private Date beforeOf;
    private Date afterOf;
    private OrderState orderState;
    private String serviceName;
    private String subServiceName;
}
