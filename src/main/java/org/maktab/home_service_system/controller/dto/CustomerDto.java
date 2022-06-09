package org.maktab.home_service_system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maktab.home_service_system.model.entity.Credit;
import org.maktab.home_service_system.model.util.UserRole;
import org.maktab.home_service_system.model.util.UserState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String firstname;
    private String lastname;
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;
    private UserState userState;
    @NotNull
    @NotEmpty
    private String username;
    @Pattern(regexp = "^(?=.*[0-9]),(?=.*[a-z])$")
    private String password;
    private Date dateOfRegister ;
    private Credit credit;
    private UserRole userRole = UserRole.CUSTOMER;
}
