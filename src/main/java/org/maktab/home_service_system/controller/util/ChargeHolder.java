package org.maktab.home_service_system.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeHolder {
    private Integer userId;
    private Long amount;
}
