package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.SubServicesDto;
import org.maktab.home_service_system.model.service.impl.SubServicesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/subServices")
public class SubServicesController {
    private final SubServicesServiceImpl subServicesService;

    public SubServicesController(SubServicesServiceImpl subServicesService) {
        this.subServicesService = subServicesService;
    }

    @PostMapping
    public ResponseEntity<SubServicesDto> save(@RequestBody SubServicesDto subServicesDto) {
        return ResponseEntity.ok(subServicesService.save(subServicesDto));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<SubServicesDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(subServicesService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SubServicesDto>> findAll() {
        return ResponseEntity.ok(subServicesService.findAllSubServices());
    }

    @GetMapping("/findByServicesId/{servicesId}")
    public ResponseEntity<List<SubServicesDto>> findByServicesId(@PathVariable String servicesId) {
        return ResponseEntity.ok(subServicesService.findSubServicesByServicesId(Integer.valueOf(servicesId)));
    }
}
