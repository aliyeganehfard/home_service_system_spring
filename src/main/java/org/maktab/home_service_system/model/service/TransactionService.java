package org.maktab.home_service_system.model.service;


import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.entity.Transaction;
import org.maktab.home_service_system.model.entity.base.Accounts;

public interface TransactionService {
    Transaction paymentMoney(Customer sender, Expert receiver, Order order);
    Transaction charge(Accounts user , Long amount);
    Transaction withdraw(Accounts user , Long amount);
}
