package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubServicesDto {
    private Integer id;
    @NotNull
    @NotEmpty
    private String name;
    private Long basePrice;
    private String description;
    private Integer servicesId;
}
