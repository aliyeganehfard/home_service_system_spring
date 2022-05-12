package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.entity.Transaction;
import org.maktab.home_service_system.model.entity.base.Accounts;
import org.maktab.home_service_system.model.exception.NotEnoughMoneyException;
import org.maktab.home_service_system.model.repository.TransactionRepository;
import org.maktab.home_service_system.model.service.TransactionService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.maktab.home_service_system.model.util.OrderState;
import org.maktab.home_service_system.model.util.TransactionType;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class TransactionServiceImpl extends GenericServiceImpl<TransactionRepository,Transaction,Integer> implements TransactionService {
    private final CustomerServiceImpl customerService;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;
    public TransactionServiceImpl(TransactionRepository transactionRepository,CustomerServiceImpl customerService,
                                  ExpertServiceImpl expertService, OrderServiceImpl orderService) {
        super(transactionRepository);
        this.customerService =customerService;
        this.expertService = expertService;
        this.orderService = orderService;
    }

    @Override
    public Transaction paymentMoney(Customer sender, Expert receiver, Order order) {
        if (order.getPrice() > sender.getCredit().getBalance())
            throw new NotEnoughMoneyException();
        sender.getCredit().setBalance(
                sender.getCredit().getBalance() - order.getPrice()
        );
        receiver.getCredit().setBalance(
                receiver.getCredit().getBalance() + order.getPrice()
        );
        order.setOrderState(OrderState.PAID);
        var transaction = Transaction.builder()
                .sender(sender).receiver(receiver).amount(order.getPrice()).transactionType(TransactionType.PAYMENT)
                .date(Date.valueOf(LocalDate.now())).time(Time.valueOf(LocalTime.now()))
                .build();

        customerService.update(sender);
        expertService.update(receiver);
        orderService.update(order);
        return super.save(transaction);
    }

    @Override
    public Transaction charge(Accounts user, Long amount) {
        user.getCredit().setBalance(
                user.getCredit().getBalance() + amount
        );
        var transaction = Transaction.builder()
                .sender(user).amount(amount).transactionType(TransactionType.CHARGE)
                .date(Date.valueOf(LocalDate.now())).time(Time.valueOf(LocalTime.now()))
                .build();
        customerService.update((Customer) user);
        return super.save(transaction);
    }

    @Override
    public Transaction withdraw(Accounts user, Long amount) {
        if (user.getCredit().getBalance() < amount)
            throw new NotEnoughMoneyException();
        user.getCredit().setBalance(
                user.getCredit().getBalance() - amount
        );
        var transaction = Transaction.builder()
                .sender(user).amount(amount).transactionType(TransactionType.WITHDREW)
                .date(Date.valueOf(LocalDate.now())).time(Time.valueOf(LocalTime.now()))
                .build();
        expertService.update((Expert) user);
        return super.save(transaction);
    }


}
