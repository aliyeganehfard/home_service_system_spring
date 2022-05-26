package org.maktab.home_service_system.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordHolder {
    private Integer customerId;
    private String oldPassword;
    private String newPassword;
    private String username;
}
