package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.ServicesDto;
import org.maktab.home_service_system.model.service.impl.ServicesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/services")
public class ServicesController {
    private final ServicesServiceImpl servicesService;

    public ServicesController(ServicesServiceImpl servicesService) {
        this.servicesService = servicesService;
    }

    @PostMapping
    public ResponseEntity<ServicesDto> save(@RequestBody ServicesDto servicesDto){
        return ResponseEntity.ok(servicesService.save(servicesDto));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ServicesDto> findById(@PathVariable String id){
        return ResponseEntity.ok(servicesService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ServicesDto>> findAll(){
        return ResponseEntity.ok(servicesService.findAllServices());
    }
}
