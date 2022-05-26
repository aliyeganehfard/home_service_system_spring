package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.OfferDto;
import org.maktab.home_service_system.model.entity.Offer;
import org.maktab.home_service_system.model.service.impl.OfferServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@CrossOrigin
@RequestMapping("/api/offer")
public class OfferController {
    private final OfferServiceImpl offerService;

    public OfferController(OfferServiceImpl offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDto> save(@RequestBody OfferDto offerDto){
        return ResponseEntity.ok(offerService.save(offerDto));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<OfferDto> findById(@PathVariable String id){
        return ResponseEntity.ok(offerService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OfferDto>> findAll(){
        return ResponseEntity.ok(offerService.findAllOffers());
    }

    @GetMapping("/findOrderOffers/{orderId}")
    public ResponseEntity<List<OfferDto>> findOrderOffers(@PathVariable String orderId){
        return ResponseEntity.ok(offerService.findOrderOffers(Integer.valueOf(orderId)));
    }
}
