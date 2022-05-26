package org.maktab.home_service_system.model.service.impl;


import org.maktab.home_service_system.controller.dto.TransactionDto;
import org.maktab.home_service_system.controller.exception.CustomerNotFoundException;
import org.maktab.home_service_system.controller.exception.ExpertNotFoundException;
import org.maktab.home_service_system.controller.exception.OrderNotFoundException;
import org.maktab.home_service_system.controller.util.ChargeHolder;
import org.maktab.home_service_system.controller.util.PaymentHolder;
import org.maktab.home_service_system.controller.util.WithdrawHolder;
import org.maktab.home_service_system.model.entity.Customer;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.entity.Order;
import org.maktab.home_service_system.model.entity.Transaction;
import org.maktab.home_service_system.controller.exception.NotEnoughMoneyException;
import org.maktab.home_service_system.model.repository.TransactionRepository;
import org.maktab.home_service_system.model.service.TransactionService;
import org.maktab.home_service_system.model.util.OrderState;
import org.maktab.home_service_system.model.util.TransactionType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerServiceImpl customerService;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerServiceImpl customerService,
                                  ExpertServiceImpl expertService, OrderServiceImpl orderService, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
        this.expertService = expertService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDto paymentMoney(PaymentHolder holder) {
        var sender = customerService.findCustomerById(holder.getCustomerId());
        var receiver = expertService.findExpertById(holder.getExpertId());
        var order = orderService.findOrderById(holder.getOrderId());

        checked(sender, receiver, order);

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
        return modelMapper.map(transactionRepository.save(transaction), TransactionDto.class);
    }

    private void checked(Customer sender, Expert receiver, Order order) {
        if (sender == null)
            throw new CustomerNotFoundException();
        if (receiver == null)
            throw new ExpertNotFoundException();
        if (order == null)
            throw new OrderNotFoundException();
    }

    @Override
    public TransactionDto charge(ChargeHolder holder) {
        var user = customerService.findCustomerById(holder.getUserId());

        if (user == null)
            throw new CustomerNotFoundException();

        user.getCredit().setBalance(
                user.getCredit().getBalance() + holder.getAmount()
        );
        var transaction = Transaction.builder()
                .sender(user).amount(holder.getAmount()).transactionType(TransactionType.CHARGE)
                .date(Date.valueOf(LocalDate.now())).time(Time.valueOf(LocalTime.now()))
                .build();
        customerService.update(user);
        return modelMapper.map(transactionRepository.save(transaction), TransactionDto.class);
    }

    @Override
    public TransactionDto withdraw(WithdrawHolder holder) {
        var user = expertService.findExpertById(holder.getUserId());

        if (user == null)
            throw new ExpertNotFoundException();

        if (user.getCredit().getBalance() < holder.getAmount())
            throw new NotEnoughMoneyException();
        user.getCredit().setBalance(
                user.getCredit().getBalance() - holder.getAmount()
        );
        var transaction = Transaction.builder()
                .sender(user).amount(holder.getAmount()).transactionType(TransactionType.WITHDREW)
                .date(Date.valueOf(LocalDate.now())).time(Time.valueOf(LocalTime.now()))
                .build();
        expertService.update(user);
        return modelMapper.map(transactionRepository.save(transaction), TransactionDto.class);
    }


}
