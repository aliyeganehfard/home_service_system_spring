package org.maktab.home_service_system.controller;

import org.hibernate.engine.internal.CacheHelper;
import org.maktab.home_service_system.controller.dto.TransactionDto;
import org.maktab.home_service_system.controller.util.ChargeHolder;
import org.maktab.home_service_system.controller.util.PaymentHolder;
import org.maktab.home_service_system.controller.util.WithdrawHolder;
import org.maktab.home_service_system.model.service.impl.TransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/payment")
    public ResponseEntity<TransactionDto> payment(@RequestBody PaymentHolder paymentHolder){
        return ResponseEntity.ok(transactionService.paymentMoney(paymentHolder));
    }

    @PostMapping("/charge")
    public ResponseEntity<TransactionDto> charge(@RequestBody ChargeHolder chargeHolder){
        return ResponseEntity.ok(transactionService.charge(chargeHolder));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody WithdrawHolder withdrawHolder){
        return ResponseEntity.ok(transactionService.withdraw(withdrawHolder));
    }
}
