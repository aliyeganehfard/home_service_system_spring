package org.maktab.home_service_system.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.util.Score;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertSearchHolder {
    private String firstname;
    private String lastname;
    private String email;
    private String serviceName;
    private String score;
}
