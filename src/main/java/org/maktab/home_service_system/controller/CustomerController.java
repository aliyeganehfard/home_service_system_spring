package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.CustomerDto;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;
import org.maktab.home_service_system.model.service.impl.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.save(customerDto));
    }

    @PutMapping
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.update(customerDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id) {
        customerService.deleteById(id);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CustomerDto>> findAll(){
        return ResponseEntity.ok(customerService.findAllCustomer());
    }

    @GetMapping("/login")
    public ResponseEntity<CustomerDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(customerService.login(loginRequest));
    }

    @GetMapping("/changePassword")
    public ResponseEntity<CustomerDto> changePassword(@RequestBody ChangePasswordHolder changePasswordHolder){
        return ResponseEntity.ok(customerService.changePassword(changePasswordHolder));
    }

    @PostMapping("/search")
    public ResponseEntity<List<CustomerDto>> search(@RequestBody CustomerSearchHolder searchHolder){
        return ResponseEntity.ok(customerService.gridSearch(searchHolder));
    }
}
