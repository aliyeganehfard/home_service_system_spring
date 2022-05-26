package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.controller.dto.TransactionDto;
import org.maktab.home_service_system.controller.util.ChargeHolder;
import org.maktab.home_service_system.controller.util.PaymentHolder;
import org.maktab.home_service_system.controller.util.WithdrawHolder;

public interface TransactionService {
    TransactionDto paymentMoney(PaymentHolder holder);
    TransactionDto charge(ChargeHolder holder);
    TransactionDto withdraw(WithdrawHolder holder);
}
