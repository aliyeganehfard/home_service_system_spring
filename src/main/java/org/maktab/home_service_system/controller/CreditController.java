package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.CreditDto;
import org.maktab.home_service_system.model.service.impl.CreditServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/credit")
public class CreditController {
    private final CreditServiceImpl creditService;

    public CreditController(CreditServiceImpl creditService) {
        this.creditService = creditService;
    }

    @PostMapping
    public ResponseEntity<CreditDto> save(@RequestBody CreditDto creditDto){
        return ResponseEntity.ok(creditService.save(creditDto));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CreditDto> findById(@PathVariable String id){
        return ResponseEntity.ok(creditService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CreditDto>> findAll(){
        return ResponseEntity.ok(creditService.findAllCredit());
    }

    @GetMapping("/findByAccountId/{accountId}")
    public ResponseEntity<CreditDto> findByAccountId(@PathVariable String accountId){
        return ResponseEntity.ok(creditService.findByAccountId(Integer.valueOf(accountId)));
    }
}
